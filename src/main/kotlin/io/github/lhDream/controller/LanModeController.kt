package io.github.lhDream.controller

import com.alibaba.fastjson2.JSONObject
import io.github.lhDream.constant.ProtocolConstant.getRoomList
import io.github.lhDream.constant.ProtocolConstant.resRoomList
import io.github.lhDream.constant.ProtocolConstant.version
import tornadofx.*
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetSocketAddress
import java.net.SocketAddress
import kotlin.concurrent.thread

/**
 *
 */
class LanModeController: Controller() {

    private val skt = DatagramSocket(17643)
    private val receiver = DatagramSocket(17644)

    private var userName = "user"

    /**
     * 创建房间
     */
    fun createRoom(){
        if(skt.isClosed && !skt.isConnected){
            skt.bind(InetSocketAddress("0.0.0.0",123))
        }
    }

    fun sendUDP(data:String){
        val ip = "255.255.255.255"
        val port = 13625
        val msg = data.toByteArray()
        receiver.send(DatagramPacket(msg,msg.size,InetSocketAddress(ip,port)))
    }

    /**
     * 获取房间列表
     */
    fun getRoomList(){
        val msg = """
            {
                "type": "$getRoomList",
                "version": $version
            }
        """.trimIndent()
        this.sendUDP(msg)
    }

    fun listenRoomList(){
        thread {
            while (true){
                runCatching {
                    val buf = byteArrayOf()
                    val dataPack = DatagramPacket(buf,buf.size)
                    receiver.receive(dataPack)
                    val recStr = String(buf)
                    val json = JSONObject.parse(recStr)
                    when(json["type"]){
                        "getRoomList" ->{
                            val msg = """
                                {
                                    "type": "$resRoomList",
                                    "userName": "$userName",
                                    "version": $version
                                }
                            """.trimIndent().toByteArray()
                            receiver.send(DatagramPacket(msg,msg.size,dataPack.socketAddress))
                        }
                        "resRoomList" ->{

                        }
                    }
                }.getOrElse {
                    it.printStackTrace()
                }
            }

        }
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