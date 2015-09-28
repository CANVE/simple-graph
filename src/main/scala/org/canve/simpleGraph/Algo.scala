package org.canve.simpleGraph
import collection.immutable.HashMap

class AbstractVertexCache {
  def apply = new AbstractVertexCache
}

/*
 * 
 */
class AlgoCache
  [ID,
   VertexCache <: AbstractVertexCache, 
   Vertex <: AbstractVertex[ID], 
   Edge <: AbstractEdge[ID]]
   (vertexCacheFactory: AbstractVertexCache, graph: AbstractGraph[ID, Vertex, Edge]) {

  private val vertexCacheIndex: Map[ID, AbstractVertexCache] = 
    graph.vertexIterator.map(vertex => (vertex._1, vertexCacheFactory.apply)).toMap
  
  def cache(id: ID) = vertexCacheIndex.get(id)
}

case class AllPathsAlgo
  [ID, 
   Vertex <: AbstractVertex[ID], 
   Edge <: AbstractEdge[ID]]
   (graph: AbstractGraph[ID, Vertex, Edge], 
    origin: Vertex,
    target: Vertex) {

  protected class MyVertexCache extends AbstractVertexCache {
    val visited: Boolean = false
    val successfulPathsList = List()
  } 
  
  object vertexCache extends AlgoCache[ID, MyVertexCache, Vertex, Edge](new MyVertexCache, graph)
  
  
  
}
  




