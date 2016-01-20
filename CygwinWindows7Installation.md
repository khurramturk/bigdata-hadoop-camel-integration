# Introduction #
The Add Host Targets Wizard is an application built into the Enterprise Manager Cloud Control console. It offers GUI-rich, interactive screens that enable you to install Oracle Management Agents (Management Agents) on unmanaged hosts and convert them to managed hosts, so that they can be monitored and managed in Enterprise Manager Cloud Control.

When you use the Add Host Targets Wizard to install a Management Agent on a host running on Microsoft Windows, as a prerequisite, you must install Cygwin and start the SSH Daemon on the host. To do so, follow the steps listed in Section 7.3.

Cygwin is essentially a utility that offers a Linux-like environment on a Microsoft Windows host. Technically, it is a DLL (cygwin1.dll) that acts as a Linux API layer providing substantial Linux API functionality. Once you install Cygwin, you can configure the SSH Daemon on the host. The Add Host Targets Wizard is certified and supported with Cygwin 1.7.

The SSH Daemon enables the Add Host Targets Wizard to establish SSH connectivity between the OMS host and the host on which you want to install a Management Agent. Using this connectivity, the wizard transfers the Management Agent software binaries to the destination host over SSH protocol, installs the Management Agent, and configures it.
Add your content here.


# Details #

To install cygwin following is procedure:
  * Access the following URL, then click Install Cygwin:
> > http://www.cygwin.com/
  * Download the 32-bit version (if you are running a 32-bit version of Microsoft Windows) or the 64-bit version (if you are running a 64-bit version of Microsoft Windows) of the Cygwin setup executable.
  * Run the setup executable, then click Next to proceed.
  * On the Choose Installation Type screen, select Install from Internet, then click Next.
  * On the Choose Installation Directory screen, enter C:\cygwin as the Root Directory, then click Next.
  * On the Select Local Package Directory screen, select a directory on your local machine where you want to store the downloaded installation files, then click Next.
  * On the Select Connection Type screen, select appropriate settings to connect to the internet, then click Next.
  * On the Choose Download Site(s) screen, select any site from the available list, then click Next.
  * On the select packages screen, ensure that you select the following packages, then click Next:

  1. From the Archive category, select unzip and zip.
  1. From the Net category, select openssh and openssl.

  * On the Installation Status and Create Icons screen, do not make any changes. Click Finish to complete the installation process.