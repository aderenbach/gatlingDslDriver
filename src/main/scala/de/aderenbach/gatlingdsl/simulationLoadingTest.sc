import scala.io.Source

object simulationLoadingTest {

  class Simulation(name: String, baseUrl: String,scnConfig:List[ScenarioConfig])
  class RampUp(rampUp:String)
  class ScenarioConfig(scn: Scenario, userCount: Int, rampUp: RampUp)
  class Scenario(name: String)

  val dsl = """Simulation: a test simulation
    BaseURL: http://localhost
    Scenarios:
        testScen with 20 users at once
        testScen2 with 20 users over 20 seconds
            """

  implicit class Regex(sc: StringContext) {
    def r = new util.matching.Regex(sc.parts.mkString, sc.parts.tail.map(_ => "x"): _*)
  }

  val result = for (line <- Source.fromString(dsl).getLines()) yield {
    line match {
      case r"\s*Simulation: (.*)$name" => "Name" -> name
      case r"\s*BaseURL: (.*)$url"  => "BaseURL" -> url
      case r"\s*Scenarios:.*" => null
      case r"\s*" => null
      case _ => "Scenario" -> line.trim
    }
  }
  val (simConfig,scenarios) = result filter (e => e != null) partition(e => e._1 != "Scenario")

  val simConfigMap =  simConfig toMap

  val scnList = for ((id,name) <- scenarios) yield name

  val scnTuples = for (scn <- scnList) yield {
    println(scn)
    scn match {
      case r"(\w*)$name with (\d+)$userCount users (.*)$rampUp"
            => new ScenarioConfig(new Scenario(name),userCount.toInt,new RampUp(rampUp))
      case _ => throw new RuntimeException("Can't part simulation")
    }
  }

  val sim = new Simulation(simConfigMap("Name"),simConfigMap("BaseURL"), scnTuples.toList)






}