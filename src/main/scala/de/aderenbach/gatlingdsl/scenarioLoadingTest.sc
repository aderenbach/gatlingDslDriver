import scala.io.Source

object scenarioLoadingTest {


  class Scenario(name: String, actions: List[String])

  // -- Scenario loading

  val scenarioDsl = """Scenario: call the server 2

                      User calls GET /
                    """

  implicit class Regex(sc: StringContext) {
    def r = new util.matching.Regex(sc.parts.mkString, sc.parts.tail.map(_ => "x"): _*)
  }

  val result = (for (line <- Source.fromString(scenarioDsl).getLines()) yield {
    line match {
      case r"\s*Scenario: (.*)$name" => "Name" -> name
      case r"\s*" => null
      case _ => "Action" -> line.trim
    }
  }) filter (e => e != null)


  val (scenarioConfig,actions) = result filter (e => e != null) partition(e => e._1 != "Actions")

  val scenarioConfigMap =  scenarioConfig toMap


  new Scenario(scenarioConfigMap("Name"),actions map (e=> e._2) toList)


}