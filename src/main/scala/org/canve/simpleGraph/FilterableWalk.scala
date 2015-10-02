package org.canve.simpleGraph

abstract sealed class       EdgeDirectionAllowed
object AnyDirection extends EdgeDirectionAllowed
object Ingress      extends EdgeDirectionAllowed
object Egress       extends EdgeDirectionAllowed

/*
 * cake-layer trait providing the ability to walk a graph with functional-style filters 
 */
trait FilterableWalk[ID, Vertex <: AbstractVertex[ID], Edge <: AbstractEdge[ID]] {
  self: SimpleGraph[ID, Vertex, Edge] =>
    
 /* 
  * returns a collection of vertex's edge peers, applying a @filter function 
  * 
  * a vertex's edges are filtered by a function that is free to perform any 
  * filtering logic based on the edge, and peer vertex properties  
  * 
  * @filter - function returning true if the vertex should pass through
  *  
  */
  def vertexEdgePeers(id: ID, filterFunc: FilterFuncArguments => Boolean): Set[Vertex] = {
    
    (edgeIndex
       .vertexEdges(id).getOrElse(Set()).toList
       .map(edge => FilterFuncArguments(Egress, edge, vertex(edge.id2).get)) ++
     reverseEdgeIndex
       .vertexEdges(id).getOrElse(Set()).toList
       .map(edge => FilterFuncArguments(Ingress, edge, vertex(edge.id1).get)))
          .filter(filterFunc)
          .map(_.peer)
          .toSet
  } 
  
  /*
   * a representation encouraging readable `filterFunc` implementations
   */
  case class FilterFuncArguments(direction: EdgeDirectionAllowed, edge: Edge, peer: Vertex) 
}
