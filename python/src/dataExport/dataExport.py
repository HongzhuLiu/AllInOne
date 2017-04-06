#!/usr/bin/python2
# _*_encoding:utf-8_*_
from elasticsearch import Elasticsearch
from socket import *
import logging.config
import time
import yaml
import sys
import os

logging.config.fileConfig("logging.conf")
logger = logging.getLogger("dataExport")

config = yaml.load(file('config.yaml'))
hosts = config["es"]["hosts"]
index = config["es"]["index"]
startTime = config["es"]["startTime"]
endTime = config["es"]["endTime"]
originalLogField = config["es"]["originalLogField"]
timestampField = config["es"]["timestampField"]
IP=config["enterprise"]["ip"]
PORT=config["enterprise"]["port"]
timeFlagFile=sys.path[0]+os.path.sep+"timeFlag"
logger.info("配置文件加载完毕！")

#记录查询到的时间点
def writeTimeFlag(startTimeStamp):
    try:
        file = open(timeFlagFile,"w")
        logger.info("记录查询时间点:"+str(startTimeStamp))
        file.writelines(str(startTimeStamp))
        file.flush()
    except:
        logger.error("记录查询时间点失败")
    finally:
        file.close()

def readTimeFlag():
    try:
        file = open(timeFlagFile,"r")
        logger.info("读取查询时间点:"+str(startTimeStamp))
        return file.readline()
    except:
        logger.error("读取查询时间点失败")
    finally:
        file.close()

startTimeStamp = 0
if startTime != None:
    startTimeStamp = int(time.mktime(startTime.timetuple())*1000)

endTimeStamp = -1
if endTime != None:
    endTimeStamp = int(time.mktime(endTime.timetuple())*1000)

writeTimeFlag(startTimeStamp)

socket = socket(AF_INET,SOCK_DGRAM)
socket.connect((IP,PORT))

def queryData(startTimeStamp,endTimeStamp):
    es = Elasticsearch([hosts])
    queryStr = '{"filter":{"range":{'+timestampField+':{"gte":' + str(startTimeStamp)+',"lt":' + str(endTimeStamp)+'}}}}'
    res = es.search(index=index,
                    scroll='300s',
                    search_type='scan',
                    size=100,
                    body=queryStr)
    sid = res['_scroll_id']
    while (1):
        try:
            rs = es.scroll(scroll_id=sid, scroll='300s')
            for hit in rs['hits']['hits']:
                originalLog=hit["_source"][originalLogField]
                logger.debug(u"message:"+str(originalLog))
                socket.send(originalLog+"\n")
        except:
            break

#查询间隔(s)
intervalTime=5*1000;
while(1):
    startTimeStamp = long(readTimeFlag())
    tEndTimeStamp=startTimeStamp+intervalTime;
    if endTime == None:
        endTimeStamp = int(time.time()*1000)
    elif tEndTimeStamp>endTimeStamp:
        logger.info("导出数据结束!")
        break
    if tEndTimeStamp<=endTimeStamp:
        queryData(startTimeStamp,tEndTimeStamp)
        startTimeStamp=tEndTimeStamp
        writeTimeFlag(startTimeStamp)
    else:
        time.sleep(5)
