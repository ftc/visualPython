package com.example

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CalculatorSpec extends AnyFlatSpec with Matchers {

  "Calculator.add" should "return the sum of two integers" in {
    Calculator.add(2, 3) shouldBe 5
    Calculator.add(-1, 1) shouldBe 0
    Calculator.add(0, 0) shouldBe 0
  }

  "Calculator.subtract" should "return the difference of two integers" in {
    Calculator.subtract(5, 3) shouldBe 2
    Calculator.subtract(0, 4) shouldBe -4
  }

  "Calculator.multiply" should "return the product of two integers" in {
    Calculator.multiply(3, 4) shouldBe 12
    Calculator.multiply(-2, 5) shouldBe -10
  }

  "Calculator.divide" should "return Right with the quotient for nonzero divisor" in {
    Calculator.divide(10, 2) shouldBe Right(5.0)
  }

  it should "return Left for division by zero" in {
    Calculator.divide(10, 0) shouldBe Left("Division by zero")
  }
}
