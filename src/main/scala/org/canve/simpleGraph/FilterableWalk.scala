package org.canve.simpleGraph

abstract sealed class       EdgeDirectionAllowed
object ToVertex     extends EdgeDirectionAllowed
object FromVertex   extends EdgeDirectionAllowed
object AnyDirection extends EdgeDirectionAllowed

/*
 * cake-layer trait providing the ability to walk a graph with functional-style filters 
 */
trait FilterableWalk[ID, Vertex <: AbstractVertex[ID], Edge <: AbstractEdge[ID]] {
  self: SimpleGraph[ID, Vertex, Edge] =>
    
 /* 
  * returns a collection of vertex's edge peers, filtered by the edge's properties
  * 
  * a vertex's edges can be filtered by a function argument (@vertexContent) that is 
  * free to perform any @Vertex specific contents examination. And/or by the 
  * direction of the edge - incoming or outgoing.
  * 
  * if either argument is not passed, a void (no-filter) filter is substituted for it.
  * 
  * @vertexFilter -    function accepting a vertex and returning true 
  * 								   if the vertex should pass through
  * 
  * @directionFilter - direction that should pass through    
  */
  def vertexEdgePeers(id: ID, 
                      vertexFilter: Vertex => Boolean = (v: Vertex) => true,
                      directionFilter: EdgeDirectionAllowed = AnyDirection): Set[Vertex] = {
    
    // filter by directionality
    val directionFiltered: Set[Edge] = directionFilter match { 
      case AnyDirection =>
        edgeIndex.vertexEdges(id).getOrElse(Set()) ++ 
        reverseEdgeIndex.vertexEdges(id).getOrElse(Set())
      case FromVertex =>
        edgeIndex.vertexEdges(id).getOrElse(Set())
      case ToVertex =>
        reverseEdgeIndex.vertexEdges(id).getOrElse(Set())        
    }  
    
    // filter by vertex content
    directionFiltered.map(edge => vertexEdgePeer(id, edge)) // TODO: refactor elsewhere for faster peer access here
                     .map(id => vertex(id).get)   
                     .filter(vertexFilter)
  } 
}
