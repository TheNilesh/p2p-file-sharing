P2P File Sharing with Network Coding

The project uses BitTorent like Technology, to upload, download files. Blocks are also encoded when route is too long for transmission.
The file is splitted into blocks, each of 65500 bytes. Each block is sent to downloader, on demand. However peer cant contact other peer directly, it have to pass his message through central server.The server decides route and network coding applicability.


Future_Work:
Server will be called super peers.
1. Multiple Super peers construct ring between them.
2. Search Query is passed from super peer to next super peer, response is collected by sending Superpeer asynchronously.
3. Now Superpeer apply network coding algo.
4. Sends other superpeers to send code /data.
5. Collects code and sends to peer in its cluster



If we use RNC, It is required to encode a message in a very large field, so it reduces the probability of failing to decode messages. An other drawback of random network coding is the increase in the data traffic. As there is no deterministic path for data delivery, all the nodes take part in relaying the data to the receiver is even if it is not necessary. As a result, the same message may be transmitted through the same link multiple times.

References:
http://ieeexplore.ieee.org/xpl/login.jsp?tp=&arnumber=6497042&url=http%3A%2F%2Fieeexplore.ieee.org%2Fiel7%2F12%2F4358213%2F06497042.pdf%3Farnumber%3D6497042
