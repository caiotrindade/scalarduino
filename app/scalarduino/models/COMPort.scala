package scalarduino.models

import java.io.OutputStream

import gnu.io.{CommPortIdentifier, SerialPort}
import play.api.Logger

/**
 * Created by caio on 12/11/15.
 */
case class COMPort(commPort: String, rate: Int = 9600) {

  private var serialOut: Option[OutputStream] = None
  private var port: Option[SerialPort] = None

  def initialize(): Unit = {
    System.setProperty("gnu.io.rxtx.SerialPorts", commPort)

    val portId = CommPortIdentifier.getPortIdentifier(commPort)

    port = Some(
      portId.open("Serial Communication", rate)
      .asInstanceOf[SerialPort]
    )

    serialOut = Some(port.get.getOutputStream)

    port.get.setSerialPortParams(
      rate,
      SerialPort.DATABITS_8,
      SerialPort.STOPBITS_1,
      SerialPort.PARITY_NONE
    )
  }

  def close(): Unit = {
    (serialOut, port) match {
      case (Some(sOut), Some(p)) =>
        sOut.close()
        p.close()

      case (Some(sOut), _) => sOut.close()
      case (_, Some(p)) => p.close()
      case (_, _) => //Do nothing
    }
  }

  def sendData(data: Int): Unit = {
      if(serialOut.isDefined) {
        serialOut.get.write(data)

        Logger.debug(s"sent data to arduino: ${data}")
      }
  }

}
