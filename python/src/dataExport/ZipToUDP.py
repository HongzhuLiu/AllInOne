#!/usr/bin/python2
# _*_encoding:utf-8_*_
from socket import *
import logging.config
import yaml
import os
import sys
import zipfile

logging.config.fileConfig("logging.conf")
logger = logging.getLogger("dataExport")
config = yaml.load(file('conf/zip_to_udp.yaml'))
IP=config["enterprise"]["ip"]
PORT=config["enterprise"]["port"]
filePath=config["zip"]["filePath"]
fileFlag=sys.path[0]+os.path.sep+"fileFlag"
logger.info("配置文件加载完毕！")

socket = socket(AF_INET,SOCK_DGRAM)
socket.connect((IP,PORT))

fileNameSet= set()

def read_zip(file_name):
    if not os.path.exists(file_name):
        logger.info(str(file_name)+"不存在...")
        return;
    try:
        zip_file = zipfile.ZipFile(file_name)
        for names in zip_file.namelist():
            file_data = zip_file.read(names)
            for line in file_data.split("\r\n"):
                logger.info("line="+line)
                socket.send(line)
        write_flag(fileFlag,file_name)
    except:
        logger.error("读取文件失败")
    finally:
        zip_file.close()


def write_flag(fileFlag,file_name):
    try:
        file = open(fileFlag,"a")
        logger.info("保存记录文件:"+str(file_name))
        file.write(str(file_name)+"\n")
    except:
        logger.error("保存记录文件失败")
    finally:
        file.close()

def read_flag(fileFlag):
    if os.path.exists(fileFlag):
        try:
            file = open(fileFlag,"r")
            for line in file:
                line=line.split("\n")[0]
                fileNameSet.add(line)
                logger.info("读取记录文件:"+str(line))
        except:
            logger.error("读取记录文件失败")
        finally:
            file.close()


def readRootDir(rootDir):
    for lists in os.listdir(rootDir):
        path = os.path.join(rootDir, lists)
        if os.path.isdir(path):
            readRootDir(path)
        else:
            if path not in fileNameSet:
                read_zip(path)
            else:
                logger.info("文件已被读取")

read_flag(fileFlag)
readRootDir(filePath)








