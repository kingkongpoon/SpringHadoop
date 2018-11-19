package sparkService

import java.net.URI
import java.sql.DriverManager

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody, RestController}
import org.springframework.stereotype.Controller

@Controller
@RequestMapping(Array(""))
class IndexController {

  Class.forName("com.facebook.presto.jdbc.PrestoDriver")
  def presto(sql:String):String={
    val connection=DriverManager.getConnection("jdbc:presto://bdmaster1:9999/hive/default","hive",null)
    val statement=connection.prepareStatement(sql)
    val rs=statement.executeQuery()
    //获取列数
    val colnums = rs.getMetaData().getColumnCount
    var tittle = ""
    var row = ""
    for(i <- Array.range(1, colnums + 1)){ tittle += "<th>"+rs.getMetaData.getColumnName(i)+"</th>"}
    //      tittle = "<tr>" +tittle + "<tr>"
    while(rs.next()){
      //列索引从1开始
      for(i <- Array.range(1, colnums + 1)){
        row += "<td>"+ s"${rs.getString(i)}" + "</td>"
        //          print(s"${rs.getMetaData.getColumnLabel(i)}:${rs.getString(i)} \t ")
      }
      row = "<tr>" + row + "</tr>"
    }
    """<html><body><table border="1">""" + tittle +  row +  "</table></body></html>"
  }


  @RequestMapping(Array("/index"))
  @ResponseBody
  def sparkSql(sql:String):String= {
    presto(sql)
  }



  @RequestMapping(Array("/hello"))
  @ResponseBody
  def test():String = {
    println(123)
    "Hello World!123"
  }



//查看/bi/model中是否存在该json的id
//  val conf = new Configuration()// 加載配制文件
//  val ha_host = "qmyrc"
//  val nn1 = "bdmaster1"
//  val ip1 = "bdmaster1"
//  val nn2 = "bdnode1"
//  val ip2 = "bdnode1"
////  val uri = new URI("hdfs://ns1/") // 要连接的资源位置
//  conf.set("fs.defaultFS", s"hdfs://${ha_host}")
//  conf.set("dfs.nameservices", s"${ha_host}")
//  conf.set(s"dfs.ha.namenodes.${ha_host}", s"${nn1},${nn2}")
//  conf.set(s"dfs.namenode.rpc-address.${ha_host}.${nn1}", s"${ip1}:8020")
//  conf.set(s"dfs.namenode.rpc-address.${ha_host}.${nn2}", s"${ip2}:8020")
//  conf.set(s"dfs.client.failover.proxy.provider.${ha_host}","org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider")
//  val uri = new URI(s"hdfs://${ha_host}/")
//  val fileSystem = FileSystem.get(uri,conf,"root")

  @RequestMapping(Array("/spark"))
  @ResponseBody
  def sparkprocess(id:String):String= {
    val json_path = "/bi/model/" + id + ".json"
//    System.setProperty("hadoop.home.dir", "D:\\download\\分布式工具\\hadoop\\hadoop-common-2.2.0-bin")
//    val fs = FileSystem.get(uri,conf,"root")
//    if(fileSystem.exists(new Path(json_path))){
    if(id.length > 4){
      val rt = Runtime.getRuntime()
//      try{
        rt.exec(s"spark-submit --master yarn-cluster --name ${id} --class sparkprocess /home/hadoop/data/jarlib/tangli/SparkEtlCode/qmtec-peony-1.0-SNAPSHOT.jar ${id}")
//      }catch{
//        case ex: Throwable => println("found a unknown exception"+ ex)
      println(s"已执行 ${id}")
      s"已执行 ${id}"
    }
//      println(rte.waitFor())
//      println(s"已执行 ${id}")
//      s"已执行 ${id}"
//    }
    else {
      println(s"无 ${id} 这个id")
      s"${id} 错误"
    }







//    val files = fs.listStatus(new Path("/bi/model/"))
//    val json_file = files.filter( _.getPath.getName.contains("json"))
//    var text = ""
//    json_file.map( x => text += x.getPath.getName)
//    val json_name = scala.collection.mutable.ArrayBuffer[String]()
//    json_file.map( x => json_name.append(x.getPath.getName))
    //    if(json_name.contains(json_id)){"true"}
    //    else{"fasle"}
  }
}
