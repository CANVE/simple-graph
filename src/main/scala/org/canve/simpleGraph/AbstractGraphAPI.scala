package org.canve.simpleGraph

/*
 * API definition
 */
abstract class AbstractGraph[ID, Vertex <: AbstractVertex[ID], Edge <: AbstractEdge[ID]]  {
      
  def += (vertex: Vertex): AbstractGraph[ID, Vertex, Edge] 
  
  def += (edge: Edge): AbstractGraph[ID, Vertex, Edge] 
      
  def vertex(id: ID): Option[Vertex]
  
  def vertexEdges(id: ID): Set[Edge] 
  
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

abstract class PackageException(errorText: String) extends Exception 

case class SimpleGraphDuplicate(errorText: String) extends PackageException(errorText: String)

case class SimpleGraphInvalidEdge(errorText: String) extends PackageException(errorText: String)
