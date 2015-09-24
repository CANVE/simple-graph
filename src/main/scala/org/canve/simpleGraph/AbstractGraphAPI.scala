package org.canve.simpleGraph

/*
 * API definition
 */
abstract class AbstractGraph[ID, Vertex <: AbstractVertex[ID], Edge <: AbstractEdge[ID]]  {
      
  def addNode(node: Vertex): AbstractGraph[ID, Vertex, Edge] 
  
  def getNode(id: ID): Option[Vertex]
  
  def addEdge(edge: Edge): AbstractGraph[ID, Vertex, Edge] 
  
  def getNodeEdges(id: ID): Set[Edge] 
  
  //def getEdges(node1: Int, node2: Int): Option[List[EdgeType]]
      
  //def getEdges(node1: Int, edgeKind: String, node2: Int): Option[List[EdgeType]] 
  
}

/*
 * trait to be mixed in by user code for making their nodes graph friendly
 */
trait AbstractVertex[ID] {  
  val id: ID  
} 

/*
 * trait to be mixed in by user code for making their edges graph friendly
*/
trait AbstractEdge[ID] {
  val id1: ID
  val id2: ID
}

case class SimpleGraphException(errorText: String) extends Exception
