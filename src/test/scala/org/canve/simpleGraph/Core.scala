import org.canve.simpleGraph._
import utest._
import utest.ExecutionContext.RunNow
import scala.util.{Try, Success, Failure}

object Core extends TestSuite {
  
  val tests = TestSuite {
    "Core" - new CoreTest  
  }  
  
  val results = tests.run()
}

class CoreTest {
  
  case class Node(
    id: Int, 
    name: String,
    kind: String
  ) extends AbstractVertex[Int]
                  
  case class Relation(
    id1: Int,
    Kind: String,
    id2: Int
  ) extends AbstractEdge[Int]
                  
  val graph = new SimpleGraph[Int, Node, Relation]
  
  val Node3 = Node(3, "foo3", "bar")
  
  graph.addNode(Node3)
  assert(Try(graph.addNode(Node(3, "error", "error"))).isFailure)
  assert(Try(graph.addNode(Node3)).isFailure) // we can't follow this semantic for edges though
  assert(graph.getNode(3).get == Node3)
  
  assert(graph.getNode(4).isEmpty)
  val Node4 = Node(4, "foo4", "bar")
  graph.addNode(Node4)
  
  assert(graph.getNode(4).get == Node4)    
  assert(graph.getNode(4).get == Node4)    
  assert(graph.getNode(4).get != Node3)
  
  val Node5 = Node(5, "foo5", "bar")
  graph.addNode(Node5)
  
  val relationA = Relation(3, "relates to", 4)
  val relationB = Relation(3, "relates to", 5)
  
  graph.addEdge(relationA)
  graph.addEdge(relationB)
  assert(graph.getNodeEdges(3).size == 2)
  
  assert(Try(graph.addEdge(relationA.copy())).isFailure)
  assert(graph.getNodeEdges(3).size == 2)
  
  val relationC = Relation(5, "relates back to", 3)
  graph.addEdge(relationC)
  assert(Try(graph.addEdge(relationC)).isFailure)
  assert(graph.getNodeEdges(3).size == 3)
  
  graph.addEdge(Relation(3, "relates back to", 5))
  assert(graph.getNodeEdges(3).size == 4)
  
}