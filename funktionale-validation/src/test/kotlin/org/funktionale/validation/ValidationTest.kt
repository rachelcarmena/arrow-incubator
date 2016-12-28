package org.funktionale.validation

import org.funktionale.either.Disjunction
import org.testng.Assert.*
import org.testng.annotations.Test

data class ExampleForValidation(val number: Int, val text: String)

class ValidationTest {

    @Test
    fun validationTest() {
        val d1 = Disjunction.right(1)
        val d2 = Disjunction.right(2)
        val d3 = Disjunction.right(3)

        val validation = Validation(d1, d2, d3)
        assertFalse(validation.hasFailures)
        assertEquals(validation.failures, listOf<String>())
    }

    @Test
    fun validationTestWithError() {
        val d1 = Disjunction.right(1)
        val d2 = Disjunction.left("Not a number")
        val d3 = Disjunction.right(3)

        val validation = Validation(d1, d2, d3)
        assertTrue(validation.hasFailures)
        assertEquals(validation.failures, listOf("Not a number"))
    }

    @Test
    fun validate2Test() {
        val r1 = Disjunction.right(1)
        val r2 = Disjunction.right("blahblah")
        val l1 = Disjunction.left("fail1")
        val l2 = Disjunction.left("fail2")
        assertEquals(
                validate(r1, r2, ::ExampleForValidation),
                Disjunction.right(ExampleForValidation(1, "blahblah"))
        )
        assertEquals(
                validate(r1, l2, ::ExampleForValidation),
                Disjunction.left(listOf("fail2"))
        )
        assertEquals(
                validate(l1, l2, ::ExampleForValidation),
                Disjunction.left(listOf("fail1", "fail2"))
        )
    }
}