## What is WConnect?
WConnect is Windows-intended, Java application created after Wi-Fi Connector App which I've built over a year ago. Unlike Wi-Fi Connector which was Less-polished, potentially buggy, WConnect is much more improved version of it. So, it's Wi-Fi Connector but new and better one. 

### Let's Summarize What Features WConnect does Support:
- Can Connect to any Wi-Fi ✔
- Can Disconnect from Current Wi-Fi ✔
- Can Reset Wi-Fi (Network) Settings ✔
- Can tell you about your Wi-Fi (like Wi-Fi name, IP address, Loopback address) ✔
- Can write "save.txt" file Locally wherever the WConnect is running, in which you can see your Wi-Fi name and password (if you forgot) ✔
- Can be Downloaded as an EXE file ✔
- Tiny feature: Preferably, click on "Esc" button keyboard (Escape) to close the App window. ✔

#### How does WConnect manage to connect to my Wi-Fi? (Safety): 
WConnect doesn't know your Wi-Fi name or password, and it never collects those details for any purposes. Instead When you input your Wi-FI name (SSID) and password, WConnect invokes direct command to connect to Wi-Fi based on SSID and password that you provided.

Command which WConnect uses to **Connect** to your Wi-Fi:</br>
``netsh wlan connect name=\ + YourSSID + \``</br>
</br>Command which WConnect uses to **Disconnect** From your currently connected Wi-Fi:</br>
``netsh wlan disconnect``</br>
</br>Command which WConnect uses to **Reset Network Settings** of your Wi-Fi:</br>
``netsh int ip reset`` and ``netsh winsock reset``
# Answer to Extra Useful questions:</br>
- Does WConnect Run on Java 8? **Absolutely**! WConnect runs on Both Java 8 and any Latest Versions of JDK.</br>
- Does WConnect work on other Operating Systems? **Sadly No**. For now, it's Only operational on Windows. But I might implement Multi-OS Support in future.</br>
- What happens if my Router is intact, but my computer can't detect Wi-Fi? Can it Connect during those times? **Also No**, Because if Wi-Fi isn't detected by the computer, WConnect's command won't work and fail to connect.</br>
- Will WConnect be maintained as much as RecipeDeck? **Highly likely, Yes**.</br>
- What to do if I'm unable to connect to my Wifi? **Make Sure that You are Typing exact same, valid name and Exact same password as your Wi-Fi has.**
