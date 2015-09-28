import org.canve.simpleGraph._
import utest._
import utest.ExecutionContext.RunNow
import scala.util.{Try, Success, Failure}

class Algo {
  case class Node (id: Int) extends AbstractVertex[Int]
                    
    case class Relation (id1: Int, id2: Int) extends AbstractEdge[Int]
                    
    val graph = new SimpleGraph [Int, Node, Relation]
  
    graph += (Node(1), Node(2), Node(3)) 
    graph += Relation(1, 1)
  
    //AllPathsAlgo[Int, Node, Relation](graph)
}