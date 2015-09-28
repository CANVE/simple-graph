package org.canve.simpleGraph

/*
 * Algorithm implementation of finding all paths between two vertices
 */
case class GetAllPaths [ID, Vertex <: AbstractVertex[ID], Edge <: AbstractEdge[ID]]
  (graph: AbstractGraph[ID, Vertex, Edge], 
   origin: Vertex,
   target: Vertex) extends GraphAlgo {

    /*
     * a per vertex cache type for this algorithm to use for its operation
     */
    protected class VertexCacheUnit extends AbstractVertexCacheUnit {
      val visited: Boolean = false
      val successfulPathsList = List()
      override def apply = new VertexCacheUnit
    } 
  
    val vertexCache = AlgoCacheUnit[ID, VertexCacheUnit, Vertex, Edge](new VertexCacheUnit, graph) 
    
    
}