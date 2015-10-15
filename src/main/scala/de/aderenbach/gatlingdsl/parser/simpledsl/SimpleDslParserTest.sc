import java.io.File
import de.aderenbach.gatlingdsl.parser.simpledsl.{SimpleDslSimlationBuilder, SimpleDslParser}
object SimpleDslParserTest {
  // val sim = new SimpleDslParser("/Users/kinggrass/Sources/gatling_dsl/gatling_dsl_driver/src/main/resources/simulations","testSim")
  //sim.parse

  SimpleDslSimlationBuilder(null).addHttpMethod("GET","path")

}