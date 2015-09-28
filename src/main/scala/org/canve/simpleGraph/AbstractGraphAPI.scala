package org.canve.simpleGraph

abstract trait Abstract
trait A extends Abstract
trait B extends Abstract

class AA extends A
class BB extends B

object NoCompile{
  def foo(s: Abstract) = s match {
    case _ : A =>
    case _ : B =>
  }
}

/*
 * API definition
 */
abstract class AbstractGraph[ID, Vertex <: AbstractVertex[ID], Edge <: AbstractEdge[ID]]  {
      
  def += (vertex: Vertex): AbstractGraph[ID, Vertex, Edge] 
  
  def += (edge: Edge): AbstractGraph[ID, Vertex, Edge] 
      
  def vertex(id: ID): Option[Vertex]
  
  def vertexEdges(id: ID): Set[Edge] 
  
  def vertexIterator: Iterator[(ID, Vertex)] 
  
  def EdgeIterator:   Iterator[(ID, Set[Edge])]
  
  def += (inputs: Addable*): AbstractGraph[ID, Vertex, Edge] = {
    inputs.foreach(i => i match {
      case v : AbstractVertex[ID] => += (v.asInstanceOf[Vertex])
      case e : AbstractEdge[ID]   => += (e.asInstanceOf[Edge])
    })
    this.vertexIterator.foreach(println)
    println("tests run")
    this
  } 
}


sealed abstract trait Addable

/*
 * trait to be mixed in by user code for making their nodes graph friendly
 */
abstract trait AbstractVertex[ID] extends Addable {  
  val id: ID 
} 

/*
 * trait to be mixed in by user code for making their edges graph friendly
*/
abstract trait AbstractEdge[ID] extends Addable {
  val id1: ID
  val id2: ID
}

/* package exceptions */

abstract class PackageException(errorText: String) extends Exception 

case class SimpleGraphDuplicate(errorText: String) extends PackageException(errorText: String)

case class SimpleGraphInvalidEdge(errorText: String) extends PackageException(errorText: String)
