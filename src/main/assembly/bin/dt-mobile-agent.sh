#!/bin/bash

checkUser() {
  if [ "`whoami`" == "root" ]; then
   echo "[Pre-Requirement]- You can't root to run this script"
   exit 1
  fi
}
checkUser

bin_path=`cd $(dirname $0); pwd`


APP_NAME=dt-mobile-agent
BASE=$(dirname $bin_path)
conf=${BASE}/conf/application.yml
log=${BASE}/conf/logback.xml
XMS=512m
XMX=1024m
profile=product
CLASS='com.dtstack.agent.Application'

export LANG=en_US.UTF-8
export BASE=$BASE

source /etc/profile
if [ ! -d $BASE/logs ] ; then
    mkdir -p "${BASE}/logs"
fi

if [ -z "$JAVA" ] ; then
  JAVA=$(which java)
fi

if [ -z "$JAVA" ]; then
    echo "[Pre-Requirement] - Cannot find a Java JDK. Please set either set JAVA or put java (>=1.8) in your PATH." 2>&2
    exit 1
fi
if [[ $# -eq 2 ]]; then
    profile=$2
fi

app_status() {
pid=`ps -ef|grep ${CLASS}|grep -v grep|awk '{print $2}'`

app_wc=`echo ${pid} | grep -v ^$ | wc -l`
if [ ${app_wc} -eq 1 ]; then
    if [ -z $1 ]; then
        echo -e "[STATUS] - $APP_NAME $pid is running ..."
    fi
    return 1
elif [ ${app_wc} -gt 1 ]; then
    echo -e "[STATUS] - $APP_NAME has $app_wc process is running !!!"
    return 2
else
    if [ -z $1 ]; then
        echo -e "[STATUS] - $APP_NAME is stopped."
    fi
    return 0
fi
}

app_status 1
app_status=$?

app_stop() {
if [[ app_status -eq 0 ]];then
  echo "[STOP] - $APP_NAME is not running."
  exit
fi

echo -e "[STOP] - stopping $APP_NAME $pid ... "
[ -n "$pid" ] && kill $pid

ret=$?
waitTimes=1
while [ ${ret} = 0 -a ${waitTimes} -lt 20 ] ;do
            app_status 1
            app_status=$?
            if [[ app_status -eq 0 ]]; then
                echo -e "[STOP] - stopped app."
                break
            fi
            sleep 1
            echo -e "[STOP] - stop app ${waitTimes}th times."
            ((waitTimes--))
done
}

java_start(){
  if [[ app_status -eq 0 ]]; then
    echo "[START] - $APP_NAME starting."
    if [ -e ${conf} -a -e ${log} ]; then
        JAVA_OPTS="-server -Xms${XMS} -Xmx${XMX} -Xloggc:${BASE}/logs/gc.log -verbose:gc -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${BASE}/logs"
        APP_OPTS="--spring.profiles.active=${profile} --spring.config.location=${conf} --logging.config=${log}"
        for i in ${BASE}/lib/*;
          do CLASSPATH=$i:"$CLASSPATH";
        done
        if [[ $1 -eq 0 ]]; then
            ${JAVA} ${JAVA_OPTS} -classpath .:$CLASSPATH $CLASS $APP_OPTS 1>/dev/null 2>&1 &
        elif [[ $1 -eq 1 ]]; then
            ${JAVA} ${JAVA_OPTS} -classpath .:$CLASSPATH $CLASS $APP_OPTS
        fi
        pid=$!
        if [[ -n pid ]]; then
            echo "[START] - $APP_NAME running with ${pid}"
        else
            echo "[START] - $APP_NAME running Fail..."
        fi
    else
        echo '[Pre-Requirement] - Configuration file not found!!!'
    fi
  elif [[ app_status -eq  1 ]]; then
    echo "[START] - $APP_NAME is already running."
  fi
}

app_start() {
java_start 0
}


app_console() {
java_start 1
}

app_restart() {
if [[ app_status -eq 1 ]];then
app_stop
fi
app_start 0
}

app_log(){
  if [ -e $BASE/logs/common.log ]; then
    tail -500f $BASE/logs/common.log
  else
    echo "[ERROR] - common.log can't find~~"
  fi
}

case "$1" in
  start)
    app_start
    ;;

  console)
    app_console
    ;;

  stop)
    app_stop
    ;;

  restart)
    app_restart
    ;;

  status)
    app_status
    ;;
  log)
    app_log
    ;;

  *)
    echo "Usage: $0 {console|start|stop|restart|status}"
    exit 1
    ;;

esac
