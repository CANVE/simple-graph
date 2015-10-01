package org.canve.simpleGraph.algo

import org.canve.simpleGraph._

/*
 * Algorithm implementation of finding all paths between two vertices,
 * sorted by path length.
 * 
 * Traverses starting from the origin vertex, visiting each vertex exactly once.
 * Backtracking from the target vertex, it stores a set of the paths reaching the 
 * target vertex in the cache of each vertex being part of such path.
 * 
 */
case class GetAllPaths[ID, Vertex <: AbstractVertex[ID], Edge <: AbstractEdge[ID]]
  (graph: AbstractGraph[ID, Vertex, Edge], 
   origin: ID,
   target: ID) extends GraphAlgo {
    
    var neverRun = true
  
    /*
     * a per vertex cache type for this algorithm to use for its operation
     */
    protected class selfCacheUnit {
      var visited: Boolean = false
      var successPath: Set[List[ID]] = Set() 
    } 
  
    private val cache: Map[ID, selfCacheUnit] = 
      graph.vertexIterator.map(vertex => (vertex._1, new selfCacheUnit)).toMap

    private def traverse(self: ID): Boolean = {
      val selfCache = cache(self)
      
      if (selfCache.visited) 
        return (selfCache.successPath.nonEmpty)
      else 
        selfCache.visited = true
        
      if (self == target) true   
      
      selfCache.successPath = graph.vertexEdgePeers(self).flatMap(peer => traverse(peer) match {
        case true  => cache(peer).successPath ++ cache(peer).successPath
        case false => List()
      })
      
      selfCache.successPath.filter(_.nonEmpty).nonEmpty
    }
    
    def run: Option[List[List[ID]]] = {
      
      if (!neverRun) throw SimpleGraphAlgoException("GraphAlgo object $this already run")  
      
      neverRun = false
      
      if (graph.vertex(origin).isEmpty) throw SimpleGraphInvalidVertex("vertex with id $origin is not part of the graph provided to the algorithm")
      if (graph.vertex(target).isEmpty) throw SimpleGraphInvalidVertex("vertex with id $origin is not part of the graph provided to the algorithm")
      
      traverse(origin) match {
        case false => {
          assert(cache(origin).successPath.forall(_.isEmpty))
          None
        }
        case true => Some(cache(origin).successPath.toList.sortBy(_.length))
      }     
    }
}

object Foo { org.canve.simpleGraph.algo.GetAllPaths }