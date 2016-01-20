# Introduction #

Install and run Hadoop in Windows & Write and run your first Hadoop program.


# Hadoop Installation on Windows 7 #

This wiki gives a step by step tutorial about installing Hadoop in Windows-run a pseudo-distribution mode-start and monitor daemons-run your first program!:

  * **STEP 1)** Install cygwin from [here](https://code.google.com/p/bigdata-hadoop-camel-integration/wiki/CygwinWindows7Installation)
> Add it installation path of of Cygwin in windows environment variable PATH e.g.
```
C:\cygwin64\bin
```
  * **STEP 2)** Install SSH Server from [here](https://code.google.com/p/bigdata-hadoop-camel-integration/wiki/OpenSSHServerWindowsInstallation) .

  * **STEP 3)** create the folder for JAVA\_HOME e.g.
```
C:\jdk1.6.0_38
```
> Copy all the subfolder of your already installed Jre e.g.
```
copy data from c\program files\java\jre<version> to C:\jdk1.6.0_38
```


  * **STEP 4)** Getting the hadoop ready.
> > You can get the Configured hadoop version 0.23.4 (Configured perfectly) from following link
> > [Hadoop-Configured-Version](https://drive.google.com/file/d/0Bx33cC3yHVItbkVnOXFBamdrM1k/edit?usp=sharing)


> Extract the **Hadoop.zip**  from above link
```
Copy the hadoop/.bashrc ->  c/cygwin64/home/
```
> After this step copy the hadoop folder e.g.
```
Copy the hadoop/hadoop-0.23.4 ->  c/cygwin64/usr/local/
```

After that perform the following steps
  1. Make the below symbolic link as well. (just go to the directory one above hadoop-0.23.4 and enter the below command)
```
ln -s hadoop-0.23.4 hadoop
```
  1. Next lets try if all right with Hadoop. Try this! (It should work without any error)
```
hadoop version
```
  1. Run following command , so that it will do HDFS formatting and get the HDFS ready
```
hadoop namenode -format
```
  1. Now lets start the managers and nodes! then `cd usr/local/hadoop`
```
sbin/hadoop-daemon.sh start namenode
sbin/hadoop-daemon.sh start datanode 
sbin/hadoop-daemon.sh start secondarynamenode 
sbin/mr-jobhistory-daemon.sh start historyserver 
sbin/yarn-daemon.sh start nodemanager 
sbin/start-yarn.sh
```


  * **STEP 5)** open command prompt (or you can directly open the Cygwin)
```
net stop sshd
net start sshd
ssh localhost
```
  * **STEP 6)** Now try jps command it should list all java processes.

  * **STEP 7)** Urls: These demons and managers gives you a nice web view and here are the links!
```
http://localhost:50070/dfshealth.jsp (for NameNode)

http://localhost:8042/node/node (NodeManager)

http://localhost:8088/cluster (for ResourceManager)

http://localhost:19888/jobhistory (for Job History Server)
```

  * **STEP 8)** Now lets start with simple hadoop file system commands and then later run a sample program!
> a) Lets first create two directories
```
hadoop fs -mkdir hdfs://localhost/input 
```
> b) lets copy a log file that we are going to analyze . There are bunch of filesystem commands that are like linux and its pretty straight forward.
```
hadoop fs -copyFromLocal SystemOut.log hdfs://localhost/input/ 
```