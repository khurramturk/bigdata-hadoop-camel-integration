# Big Data #
Data growth challenges and opportunities are defined by Gartner analyst Doug Laney as
being three-dimensional, i.e.

  * **Volume** (increasing amount of data)

  * **Velocity** (speed of data in and out)

  * **Variety** (range of data types and sources).

Gartner, and now much of the industry, continue to use this "3Vs" model for describing big data. In 2012, Gartner updated its definition as follows:

> "Big data is high volume, high velocity, and/or high variety information assets that require new forms of processing to enable enhanced decision making, insight discovery and process optimization."

Additionally, a new V "Veracity" is added by some organizations to describe it.


## What is Hadoop? ##

Following are several definitions of Hadoop, each one targeting a different audience within the enterprise:
  * **For the executives:** Hadoop is an Apache open source software project to get value from the incredible volume/velocity/variety of data about your organization. Use the data instead of throwing most of it away.
  * **For the technical managers:** An open source suite of software that mines the structured and unstructured BigData about your company. It integrates with your existing Business Intelligence ecosystem.
  * **Engineering:** A massively parallel, shared nothing, Java-based map-reduce execution environment. Think hundreds to thousands of computers working on the same problem, with   built-in failure resilience. Projects in the Hadoop ecosystem provide data loading, higher-level languages, automated cloud deployment, and other capabilities.

## What are the components of Hadoop? ##

The Apache Hadoop project has two core components, the file store called **Hadoop Distributed File System (HDFS)**, and the programming framework called **Map-Reduce**. There are a number of supporting projects that leverage HDFS and Map Reduce. This article will provide a summary, and encourages you to get the OReily book "Hadoop The Definitive Guide", 3rd Edition, for more details.

The definitions below are meant to provide just enough background for you to use the code examples that follow.

  * **HDFS:** If you want 4000+ computers to work on your data, then you'd better spread your data across 4000+ computers. HDFS does this for you. HDFS has a few moving parts. The Datanodes store your data, and the Namenode keeps track of where stuff is stored. There are other pieces, but you have enough to get started.

  * **Map Reduce:** This is the programming model for Hadoop. There are two phases, not surprisingly called Map and Reduce. To impress your friends tell them there is a shuffle-sort between the Map and Reduce phase. The Job Tracker manages the 4000+ components of your Map Reduce job. The Task Trackers take orders from the Job Tracker. If you like Java then code in Java. If you like SQL or other non-Java languages you are still in luck, you can use a utility called Hadoop Streaming.

  * **Hadoop Streaming:** A utility to enable Map Reduce code in any language: C, Perl, Python, C++, Bash, etc.

  * **Hive and Hue:** If you like SQL, you will be delighted to hear that you can write SQL and have Hive convert it to a Map Reduce job. No, you don't get a full ANSI-SQL environment, but you do get 4000 nodes and multi-Petabyte scalability. Hue gives you a browser-based graphical interface to do your Hive work.

  * **Pig:** A higher-level programming environment to do Map Reduce coding. The Pig language is called Pig Latin. You may find the naming conventions somewhat unconventional, but you get incredible price-performance and high availability.

  * **Sqoop:** Provides bi-directional data transfer between Hadoop and your favorite relational database.

  * **Oozie:** Manages Hadoop workflow. This doesn't replace your scheduler or BPM tooling, but it does provide if-then-else branching and control within your Hadoop jobs.

  * **HBase:** A super-scalable key-value store. It works very much like a persistent hash-map (for python fans think dictionary). It is not a relational database despite the name HBase.

  * **FlumeNG:** A real time loader for streaming your data into Hadoop. It stores data in HDFS and HBase. You'll want to get started with FlumeNG, which improves on the original flume.

  * **Whirr:** Cloud provisioning for Hadoop. You can start up a cluster in just a few minutes with a very short configuration file.

  * **Mahout:** Machine learning for Hadoop. Used for predictive analytics and other advanced analysis.

  * **Fuse:** Makes the HDFS system look like a regular file system so you can use ls, rm, cd, and others on HDFS data

  * **Zookeeper:** Used to manage synchronization for the cluster. You won't be working much with Zookeeper, but it is working hard for you. If you think you need to write a program that uses Zookeeper you are either very, very, smart and could be a committee for an Apache project, or you are about to have a very bad day.

This project address these **Big data** analytics challenges using , **Hadoop**, **Apache Camel**,**MongoDB**  and **ActiveMQ**.