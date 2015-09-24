package org.canve.simpleGraph
//import scala.collection.immutable.HashMap
import collection.mutable.HashMap

/*
 * each edge is indexed in two indexes:
 * one is indexing by the first node,
 * the other by the second, so that all edges touching a node can be retrieved in O(1).
 */
class SimpleGraph[ID, Vertex <: AbstractVertex[ID], Edge <: AbstractEdge[ID]] 
  extends AbstractGraph[ID, Vertex, Edge] {
  
  // utility class for an edge index
  private class UnidirectionalEdgeIndex {
    val index = new HashMap[ID, Set[Edge]]
    
    def addEdgeToUnidirectionalIndex(key: ID, edge: Edge) = {
      index.get(key) match {
        case Some(set) => 
          set.contains(edge) match {
            case true  => throw SimpleGraphException("edge $edge already exists in the graph")
            case false => index.put(key, set + edge)
          }
        case None      => index.put(key, Set(edge))
      }
    }
    
    def getNodeEdges(id: ID): Option[Set[Edge]] = index.get(id)  
  }
  
  private val nodeIndex        = new HashMap[ID, Vertex]   
  private val edgeIndex        = new UnidirectionalEdgeIndex
  private val reverseEdgeIndex = new UnidirectionalEdgeIndex
  
  def addNode(vertex: Vertex): SimpleGraph[ID, Vertex, Edge] = {    
    nodeIndex.get(vertex.id) match {      
      case Some(vertex) => throw SimpleGraphException("node with id $id already exists in the graph")
      case None => nodeIndex += ((vertex.id, vertex))
    }
    this
  }
  
  def getNode(id: ID): Option[Vertex] = nodeIndex.get(id)
  
  def addEdge(edge: Edge): SimpleGraph[ID, Vertex, Edge] = {
    edgeIndex.addEdgeToUnidirectionalIndex(edge.id1, edge)  
    reverseEdgeIndex.addEdgeToUnidirectionalIndex(edge.id2, edge)  
    this
  }
  
  def getNodeEdges(id: ID): Set[Edge] = {
    println(edgeIndex.getNodeEdges(id))
    println(reverseEdgeIndex.getNodeEdges(id))

    edgeIndex.getNodeEdges(id).getOrElse(Set()) ++ 
    reverseEdgeIndex.getNodeEdges(id).getOrElse(Set())
  }  
}