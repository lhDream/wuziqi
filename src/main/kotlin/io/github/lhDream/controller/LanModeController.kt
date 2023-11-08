package io.github.lhDream.controller

import tornadofx.*
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetSocketAddress
import java.net.SocketAddress

/**
 *
 */
class LanModeController: Controller() {

    private val skt = DatagramSocket(17643)
    private val receiver = DatagramSocket(17644)

    /**
     * 创建房间
     */
    fun createRoom(){
        if(skt.isClosed && !skt.isConnected){
            skt.bind(InetSocketAddress("0.0.0.0",123))
        }
    }

    /**
     * 获取房间列表
     */
    fun getRoomList(){
        receiver.send(DatagramPacket("".toByteArray(),0))
    }

    /**
     * 加入房间
     */
    fun joinRoom(){

    }

    /**
     * 退出房间
     */
    fun exitRoom(){
        skt.close()
    }

}