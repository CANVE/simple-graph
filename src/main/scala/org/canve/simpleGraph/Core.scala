package org.canve.simpleGraph
//import scala.collection.immutable.HashMap
import collection.mutable.HashMap

/*
 * implementation of the `AbstractGraph` API
 * 
 * each edge is indexed in two indexes:
 * one is indexing by the first node,
 * the other by the second, so that all edges touching a node can be retrieved in O(1).
 */
class SimpleGraph[ID, Vertex <: AbstractVertex[ID], Edge <: AbstractEdge[ID]] 
  extends AbstractGraph[ID, Vertex, Edge] {

  private val vertexIndex      = new HashMap[ID, Vertex]   
  private val edgeIndex        = new UnidirectionalEdgeIndex
  private val reverseEdgeIndex = new UnidirectionalEdgeIndex
  
  // utility class for an edge index
  private class UnidirectionalEdgeIndex {
    val index = new HashMap[ID, Set[Edge]]
    
    def addEdgeToUnidirectionalIndex(key: ID, edge: Edge) = {
      index.get(key) match {
        case Some(set) => 
          set.contains(edge) match {
            case true  =>
              throw SimpleGraphDuplicate("edge $edge already exists in the graph")
            case false => index.put(key, set + edge)
          }
        case None      => index.put(key, Set(edge))
      }
    }
    
    def vertexEdges(id: ID): Option[Set[Edge]] = index.get(id)  
  }

  /*
   * public methods
   */
  
  def += (vertex: Vertex): SimpleGraph[ID, Vertex, Edge] = {    
    vertexIndex.get(vertex.id) match {      
      case Some(vertex) => throw SimpleGraphDuplicate("node with id $id already exists in the graph")
      case None => vertexIndex += ((vertex.id, vertex))
    }
    this
  }
    
  def += (edge: Edge): SimpleGraph[ID, Vertex, Edge] = {

    List(edge.id1, edge.id2).foreach(id => 
      if (vertex(id).isEmpty) throw SimpleGraphInvalidEdge("will not add edge $edge because there is no vertex with id $id"))  
    
    edgeIndex.addEdgeToUnidirectionalIndex(edge.id1, edge)  
    reverseEdgeIndex.addEdgeToUnidirectionalIndex(edge.id2, edge)  
    this
  }
  
  def vertex(id: ID): Option[Vertex] = vertexIndex.get(id)
  
  def vertexEdges(id: ID): Set[Edge] = {
    edgeIndex.vertexEdges(id).getOrElse(Set()) ++ 
    reverseEdgeIndex.vertexEdges(id).getOrElse(Set())
  }    
}

/*
 * companion object / constructors
 */
object SimpleGraph {
  
  // code credit: http://stackoverflow.com/questions/32768816/bypassing-sets-invariance-in-scala/32769068#32769068
  def apply[ID, Vertex <: AbstractVertex[ID], EDGE <: AbstractEdge[ID]]
           (vertices: Set[Vertex], edges: Set[EDGE]) = {
    
    val simpleGraph = new SimpleGraph[ID, AbstractVertex[ID], AbstractEdge[ID]]
    vertices.foreach(simpleGraph +=)
    edges.foreach(simpleGraph +=)
    simpleGraph
  }
  
}
