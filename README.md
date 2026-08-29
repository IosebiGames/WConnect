## What is WConnect?
WConnect is Java application created after Wi-Fi Connector App which I've built over a year ago. Unlike Wi-Fi Connector which was Less-polished, potentially buggy, WConnect is much more improved version of it. So, it's Wi-Fi Connector but new and better one. 

### Let's Summarize What Features WConnect does Support:
- Can Connect to any Wi-Fi ✔
- Can Disconnect from Current Wi-Fi ✔
- Can Reset Wi-Fi (Network) Settings ✔
- Can tell you about your Wi-Fi (like Wi-Fi name, IP address, Loopback address) ✔
- Can write "save.txt" file Locally wherever the WConnect is running, in which you can see your Wi-Fi name and password (if you forgot) ✔
- Can be Downloaded as an EXE file ✔

#### How does WConnect manage to connect to my Wi-Fi? (Safety):
WConnect doesn't know your Wi-Fi name or password, and it never collects those for any purposes. Instead When you input your Wi-FI name (SSID) and password, WConnect invokes direct command to connect to Wi-Fi based on SSID and password that you provided.

Command that WConnect uses to **Connect** to your Wi-Fi:
``netsh wlan connect name=\ + YourSSID + \``
Command that WConnect uses to **Disconnect** From your currently connected Wi-Fi:
``netsh wlan disconnect``
Command that WConnect uses to **Reset Network Settings** of your Wi-Fi:
``netsh int ip reset`` and ``netsh winsock reset``
