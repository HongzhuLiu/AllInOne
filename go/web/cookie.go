package main

import (
	"fmt"
	"net/http"
)


//获取cookie信息
func getCookie(w http.ResponseWriter,r *http.Request){
	cookie,_:=r.Cookie("username")
	fmt.Fprintln(w,cookie)
	for _,cookie:=range r.Cookies(){
		fmt.Fprintln(w,cookie.Name)
	}
}
