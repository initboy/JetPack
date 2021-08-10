package com.abala.io.demo

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.*
import java.nio.ByteBuffer
import java.time.Duration
import java.time.Instant

const val SRC_TXT_FILE = "io/src/file/test.txt"
const val DEST_TXT_FILE = "io/src/file/dest.txt"

const val SRC_MP4_FILE = "io/src/file/src.mp4"
const val DEST_MP4_FILE = "io/src/file/dest.mp4"

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    useDataOutputStream()
    useDataInputStream()
    useBufferedWriter()
    useBufferedReader()
    useWriteRandomAccessFile()
    useReadRandomAccessFile()
    copyViaChannel(SRC_MP4_FILE, DEST_MP4_FILE)
}

/**
 * 字节流
 */

fun useDataOutputStream() {
    var out: DataOutputStream? = null
    try {
        out = SRC_TXT_FILE.dataOutputStream()
        out.writeInt(2)//这里的写与读要成对使用，否则不能正确读出
        out.writeUTF("hello")
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        out?.flush()//flush 方法是针对BufferedOutputStream中buffer中的数据没有写入磁盘，手动调用写入的
        out?.close()//最外层的流关闭后，被装饰的流也会同时被关闭
    }
}

//字节输出流 输入输出是相对于内存而言的
@Throws(FileNotFoundException::class, IOException::class)
fun String.dataOutputStream(): DataOutputStream {
    val file = File(this)//需要写到磁盘的文件
    val fileOutputStream = FileOutputStream(file)//打开文件流
    val bufferedOutputStream = BufferedOutputStream(fileOutputStream)//缓存数据，防止频繁调用磁头写磁盘
    val dataOutputStream = DataOutputStream(bufferedOutputStream)//获得字节输出流
    return dataOutputStream
}

fun useDataInputStream() {
    var input: DataInputStream? = null
    try {
        input = SRC_TXT_FILE.dataInputStream()
        val b = input.readInt()
        val str = input.readUTF()
        println(b)
        println(str)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        input?.close()
    }
}

//字节输入流
@Throws(FileNotFoundException::class, IOException::class)
fun String.dataInputStream(): DataInputStream {
    val file = File(this)//需要写到内存的文件
    val fileInputStream = FileInputStream(file)//打开文件流
    val bufferedInputStream = BufferedInputStream(fileInputStream)//缓存数据，防止频繁调用磁头写磁盘
    val dataInputStream = DataInputStream(bufferedInputStream)//获得字节输入流
    return dataInputStream
}

/**
 * 字符流
 */

fun useBufferedWriter() {
    val txt = "hello\nworld.\n汉字"
    var writer: BufferedWriter? = null
    try {
        writer = SRC_TXT_FILE.bufferedWriter()
        writer.write(txt)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    } finally {
        writer?.close()
    }

}

@Throws(FileNotFoundException::class, IOException::class, UnsupportedEncodingException::class)
fun String.bufferedWriter(charsetName: String = "UTF-8"): BufferedWriter {
    val file = File(this)
    val fileOutputStream = FileOutputStream(file)//打开文件
    val outputStreamWriter = OutputStreamWriter(fileOutputStream, charsetName)//字符集
    val bufferedWriter = BufferedWriter(outputStreamWriter)//缓存
    return bufferedWriter
}

fun useBufferedReader() {
    var reader: BufferedReader? = null
    try {
        reader = SRC_TXT_FILE.bufferedReader()
        var line: String? = reader.readLine()
        while (line != null) {
            println(line)
            line = reader.readLine()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        reader?.close()
    }
}

@Throws(FileNotFoundException::class, IOException::class, UnsupportedEncodingException::class)
fun String.bufferedReader(charsetName: String = "UTF-8"): BufferedReader {
    val file = File(this)//需要写到内存的文件
    val fileInputStream = FileInputStream(file)//打开文件流
    val inputStreamReader = InputStreamReader(fileInputStream, charsetName)//指定字符集
    val bufferedReader = BufferedReader(inputStreamReader)//缓存
    return bufferedReader
}

/**
 * RandomAccessFile 随机读写 文件拷贝
 */

fun useWriteRandomAccessFile() {
    var writer: RandomAccessFile? = null
    try {
        writer = DEST_TXT_FILE.randomAccessFile("rw")
        writer.seek(3) //从指定的位置开始写数据
        writer.writeUTF("RandomAccessFile")
    } catch (e: Exception) {
        println("useWriteRandomAccessFile ${e.message}")
    } finally {
        writer?.close()
    }
}

fun useReadRandomAccessFile() {
    var reader: RandomAccessFile? = null
    try {
        reader = DEST_TXT_FILE.randomAccessFile("r")
        reader.seek(3) //从指定的位置开始写数据
        val content = reader.readUTF()
        println(content)
    } catch (e: Exception) {
        println("useReadRandomAccessFile ${e.message}")
    } finally {
        reader?.close()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun copyViaChannel(src: String, dest: String) {//通过通道拷贝
    val begin = Instant.now() //系统时钟当前时刻
    var srcFile: RandomAccessFile? = null
    var destFile: RandomAccessFile? = null
    try {
        srcFile = src.randomAccessFile("r")
        destFile = dest.randomAccessFile("rw")
        val srcChannel = srcFile.channel
        val destChannel = destFile.channel
        val bufferSize = 8 * 1024
        val buffer = ByteBuffer.allocate(bufferSize)
        var len = srcChannel.read(buffer)
        while (len != -1) {
            buffer.flip()//反转buffer，将限制设置为当前位置，然后将位置设置为零。如果定义了标记，则将其丢弃。
            destChannel.write(buffer)//FileChannel的write方法是从之前的通道后面继续写入
            buffer.clear()
            len = srcChannel.read(buffer)
        }
    } catch (e: Exception) {
        println("copyViaChannel ${e.message}")
    } finally {
        srcFile?.close()//这里的close 会调用 channel.close()
        destFile?.close()
    }
    val spent = Duration.between(begin, Instant.now()).toMillis()
    println("copy spent $spent ms.")
}


@RequiresApi(Build.VERSION_CODES.O)
fun copyViaStream(src: String, dest: String) {//通过流拷贝
    val begin = Instant.now() //系统时钟当前时刻
    var srcFile: FileInputStream? = null
    var destFile: FileOutputStream? = null
    try {
        srcFile = FileInputStream(src)
        destFile = FileOutputStream(dest)
        val bufferSize = 8 * 1024
        val buffer = ByteArray(bufferSize)
        var len = srcFile.read(buffer)
        while (len != -1) {
            destFile.write(buffer, 0, len)//写入读取的实际长度
            len = srcFile.read(buffer)
        }
    } catch (e: Exception) {
        println("copyViaStream ${e.message}")
    } finally {
        srcFile?.close()//这里的close 会调用 channel.close()
        destFile?.close()//FileOutputStream 可以不用调用flush()
    }
    val spent = Duration.between(begin, Instant.now()).toMillis()
    println("copy spent $spent ms.")
}

@Throws(FileNotFoundException::class, IOException::class, UnsupportedEncodingException::class)
fun String.randomAccessFile(mode: String): RandomAccessFile {
    return RandomAccessFile(this, mode)
}

