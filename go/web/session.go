package main

import (
	"sync"
	"fmt"
	"io"
	"encoding/base64"
	"net/url"
	"crypto/rand"
	"net/http"
)

//session
type Manager struct {
	cookieName string
	lock sync.Mutex
	provider Prodiver
	maxLifetime int64
}

type Prodiver interface {
	SessionInit(sid string) (Session,error)
	SessionRead(sid string) (Session,error)
	SessionDestory(sid string) error
	SessionGC(maxLifetime int64)
}

type Session interface {
	Set(key,value interface{}) error
	Get(key interface{}) interface{}
	Delete(key interface{}) error
	SessionID() string
}

var provides  = make(map[string] Prodiver)

func NewManager(provideName,cookieName string,maxLifetime int64)(*Manager,error){
	prodiver,ok:=provides[provideName]
	if !ok{
		return nil,fmt.Errorf("error")
	}
	return &Manager{provider:prodiver,cookieName:cookieName,maxLifetime:maxLifetime},nil
}

func Register(name string,provide Prodiver)  {
	if provide==nil{
		panic("session:Register provide is nil")
	}
	if _,dup:=provides[name]; dup{
		panic("session:Register called twice for provide"+name)
	}
	provides[name] = provide
}

func (manager *Manager) sessionId() string{
	b:=make([]byte,32)
	if _,err:=io.ReadFull(rand.Reader,b);err!=nil{
		return ""
	}
	return base64.URLEncoding.EncodeToString(b)
}

//session创建
func (manager *Manager) SessionStart(w http.ResponseWriter,r *http.Request) (session Session){
	manager.lock.Lock()
	defer manager.lock.Unlock()
	cookie,err:=r.Cookie(manager.cookieName)
	if err!=nil|| cookie.Value ==""{
		sid:=manager.sessionId()
		session,_=manager.provider.SessionInit(sid)
		cookie:=http.Cookie{Name:manager.cookieName,Value:url.QueryEscape(sid),Path:"/",HttpOnly:true,MaxAge:int(manager.maxLifetime)}
		http.SetCookie(w,&cookie)
	}else{
		sid,_:=url.QueryUnescape(cookie.Value)
		session,_=manager.provider.SessionRead(sid)
	}
	return
}
