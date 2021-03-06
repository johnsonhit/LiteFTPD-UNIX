# LiteFTPD-UNIX
🚧 Project developed using Intellij IDEA 🚧  
Interested friends are welcome to participate in assisting my development, see my signature for the WeChat account.  
The main reason for this flaw is that I found that the encoding problem is very serious when I use the FTP server of MacOS, and Windows can hardly use it.  
So I want to make a compatible, humanized, high-performance and low-occupancy FTP server in my spare time.  

## Current development progress:
✳️ Goal: Strong compatibility, high performance, low occupancy  
Current progress: **File upload, download, delete, move, rename function for FTP clients (or FTP-enabled programs) such as Windows, Linux, MacOS, FileZilla, FlashFXP, Thunder, Chrome, Windows Explorer, etc.**  

- [x] Extremely simple multi-user identification && permission control && default directory && allows directory setting
- [x] User directory lock
- [x] Passive mode
- [x] Active mode
- [x] Breakpoint resume function
- [x] Multi-platform client compatibility
- [x] Binary download
- [x] Binary upload
- [x] ASCII download
- [x] ASCII upload
- [x] File move/rename
- [x] Memory recycling mechanism
- [x] User Defined Settings
- [x] Idle port reclamation mechanism
- [x] Small function: Automatically judge time greeting information after successful login ( Good morning / noon / afternoon / evening / night )
- [x] long connection (noop)
- [x] User connection limit and settings (support global settings)
- [x] IP connection limit and setting (support fuzzy matching)
- [x] Configuration file (delete will be automatically generated)
- [x] Upload / Download speed limit
- [ ] Graphic Control Panel

## How to experience?

### GIF Demo

![How-to-LiteFTPD-UNIX.gif](/How-to-LiteFTPD-UNIX.gif)

### Text Description

The server ** supports most FTP client connections** for most systems, but the server itself is only required to run on Linux/MacOS systems (due to file system and encoding) due to the need to be stable.  

You can clone the project or download it directly to the local (or [click here](https://github.com/AdlerED/LiteFTPD-UNIX/releases) directly download the separate Jar package), go to the release folder, run `java -jar LiteFTPD -UNIX.jar` is fine. LiteFTPD will automatically listen to port 21 by default.  

**If you need to change the language of LiteFTPD to another language**, you can add the `-l [language]` parameter when starting Jar. For example, `java -jar LiteFTPD-UNIX.jar -l zh-cn`, currently supports `zh-cn`, `en-us`.

If the configuration file `config.prop` does not exist in the same directory, it will automatically generate one when you first run it.  
Modify the file in `config.prop`, you can modify the configuration of LiteFTPD, it is very simple and easy to use.  

Controlling user permissions is also very simple. Edit the value of the `user` variable in `config.prop`. Let's take the default example:  
```
Format: [username];[password];[permission];[directed access to the directory];[default directory after login]
user=anonymous;;r;/;/;admin;123456;r;/;/root;
```
In the default value we define the information of two users. We talk separately:  

```
User 1 login name: anonymous
User 1 password: empty
User 1 permission: r Read only (r stands for read-read, w stands for write-write, d stands for delete-delete, c stands for create-create, m stands for move-move)
Directory that User 1 allows access to: / (all subdirectories and files under the root directory)
User 1 logs in to the directory by default: /

User 2 login name: admin
User 2 password: 123456
User 2 permission: r Read only
Directory that User 2 is allowed to access: /
User 2 logs in to the directory by default: /root
```

For the same reason, you can also add more users at will.  
  
How to use LiteFTPD? Simply put, this will start:  

```
git clone https://github.com/adlered/liteftpd-unix
mv liteftpd-unix/release/LiteFTPD-UNIX.jar ./
rm -rf liteftpd-unix
java -jar LiteFTPD-UNIX.jar
```

After the first startup, LiteFTPD will automatically generate the `config.prop` configuration file in the same name directory. You can modify the configuration of LiteFTPD in the configuration file and restart LiteFTPD to take effect.  
No other files are required, you can run the FTP service only through LiteFTPD-UNIX.jar. You only need to run LiteFTPD-UNIX.jar directly on the computer with Java installed, which is very convenient.  

![Screenshot 2019-09-30 8.45.56.png](https://pic.stackoverflow.wiki/uploadImages/79a47e02-0623-427f-ae43-e08ab4be11f9.png)

## Multithreading does not occupy CPU, memory

![屏幕快照 2019-10-11 上午11.38.35.png](https://pic.stackoverflow.wiki/uploadImages/6dd550d3-825e-4fdd-828e-bd53b1104dbb.png)