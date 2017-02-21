package es.common

import scala.beans.BeanProperty;

/**
  * Created by LHZ on 2016/8/5.
  */
class TaskConfBean{
  @BeanProperty var inIndex:String=""
  @BeanProperty var inType:String=""
  @BeanProperty var outIndex:String=""
  @BeanProperty var outType:String=""
  @BeanProperty var query:String=""
  @BeanProperty var table:String=""
  @BeanProperty var startTime:Long=0
  @BeanProperty var endTime:Long=0
  @BeanProperty var sql:String=""
  @BeanProperty var windowLength:Int=5
  @BeanProperty var slideIntervar:Int=1
}
