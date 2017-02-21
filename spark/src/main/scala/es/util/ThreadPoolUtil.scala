package es.util

import java.util.concurrent.{TimeUnit, Executors, ExecutorService}

/**
  * Created by LHZ on 2016/8/5.
  */
object ThreadPoolUtil {
  lazy val threadPool=Executors.newCachedThreadPool()
  lazy val scheduleServer=Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors())

  def start(command:Runnable): Unit ={
    threadPool.submit(command)
  }

  def scheduleAtFixedRate(command:Runnable,initialDelay: Long,period: Long,unit:TimeUnit): Unit ={
    scheduleServer.scheduleAtFixedRate(command,initialDelay,period,unit);
  }

}
