package main

import (
	"fmt"
	"os"
	"github.com/jvehent/service-go"
	"github.com/go-martini/martini"
	"net/http"
)

var log service.Logger

func main() {
	var name = "GoServiceTest2"
	var displayName = "Go Service Test2"
	var desc = "This is a test Go service.  It is designed to run well."

	var s, err = service.NewService(name, displayName, desc)
	log = s

	if err != nil {
		fmt.Printf("%s unable to start: %s", displayName, err)
		return
	}

	if len(os.Args) > 1 {
		var err error
		verb := os.Args[1]
		switch verb {
		case "install":
			err = s.Install()
			if err != nil {
				fmt.Printf("Failed to install: %s\n", err)
				return
			}
			fmt.Printf("Service \"%s\" installed.\n", displayName)
		case "remove":
			s.Stop()	//先停止服务
			err = s.Remove()
			if err != nil {
				fmt.Printf("Failed to remove: %s\n", err)
				return
			}
			fmt.Printf("Service \"%s\" removed.\n", displayName)
		case "run":
			doWork()
		case "start":
			err = s.Start()
			if err != nil {
				fmt.Printf("Failed to start: %s\n", err)
				return
			}
			fmt.Printf("Service \"%s\" started.\n", displayName)
		case "stop":
			err = s.Stop()
			if err != nil {
				fmt.Printf("Failed to stop: %s\n", err)
				return
			}
			fmt.Printf("Service \"%s\" stopped.\n", displayName)
		}
		return
	}
	err = s.Run(func() error {
		// start
		go doWork()
		return nil
	}, func() error {
		// stop
		stopWork()
		return nil
	})
	if err != nil {
		s.Error(err.Error())
	}
}

var exit = make(chan struct{})

func doWork() {
	log.Info("I'm Running!")
	go Svc()
	for {
		select {
		case <-exit:
			os.Exit(0)
			return
		}
	}
}
func stopWork() {
	log.Info("I'm Stopping!")
	exit <- struct{}{}
}

func Svc() {
	m := martini.Classic()
	m.Get("/", func() string {
		return "Hello world!"
	})
	http.ListenAndServe(":9090", m)
}
