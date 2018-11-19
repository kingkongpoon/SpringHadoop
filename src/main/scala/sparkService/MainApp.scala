package sparkService

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

object MainApp {
  @SpringBootApplication
  @ComponentScan(Array("sparkService"))
  class MainServer {
  }

  def main(args:Array[String]):Unit={
    SpringApplication.run(classOf[MainServer])
  }
}
