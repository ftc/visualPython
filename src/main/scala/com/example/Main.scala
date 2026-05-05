package com.example

object Main extends App {
  println("Hello, World!")
}

object Calculator {
  def add(a: Int, b: Int): Int = a + b
  def subtract(a: Int, b: Int): Int = a - b
  def multiply(a: Int, b: Int): Int = a * b
  def divide(a: Int, b: Int): Either[String, Double] =
    if (b == 0) Left("Division by zero") else Right(a.toDouble / b)
}
