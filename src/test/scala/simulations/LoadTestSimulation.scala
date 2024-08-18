package simulations

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

//import scala.concurrent.duration.DurationInt

class LoadTestSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")

  val scn: ScenarioBuilder = scenario("Load Test Scenario")
    .exec(
      http("Add Likes Request")
        .post("/addlikes")
        .body(StringBody("""{"speakerId":1,"talkName":"Spring best practice","likes":1}""")).asJson
        .check(status.is(200))
    )
    .pause(1)


  setUp(scn.inject(constantUsersPerSec(2000).during(2)).protocols(httpProtocol));

}
