package com.cs407.lab09

/**
 * Represents a ball that can move. (No Android UI imports!)
 *
 * Constructor parameters:
 * - backgroundWidth: the width of the background, of type Float
 * - backgroundHeight: the height of the background, of type Float
 * - ballSize: the width/height of the ball, of type Float
 */
class Ball(
    private val backgroundWidth: Float,
    private val backgroundHeight: Float,
    private val ballSize: Float
) {
    var posX = 0f
    var posY = 0f
    var velocityX = 0f
    var velocityY = 0f
    private var accX = 0f
    private var accY = 0f

    private var isFirstUpdate = true

    init {
        reset()
    }

    /**
     * Updates the ball's position and velocity based on the given acceleration and time step.
     * (See lab handout for physics equations)
     */
    fun updatePositionAndVelocity(xAcc: Float, yAcc: Float, dT: Float) {
        if(isFirstUpdate) {
            isFirstUpdate = false
            accX = xAcc
            accY = yAcc
            return
        }

        // Update Position
        // Formula: l = v0 * dt + (1/6) * dt^2 * (3*a0 + a1)
        val xDist = velocityX * dT + (dT * dT / 6f) * (3 * accX + xAcc)
        val yDist = velocityY * dT + (dT * dT / 6f) * (3 * accY + yAcc)

        posX += xDist
        posY += yDist

        // Update Velocity
        // Formula: v1 = v0 + 0.5 * (a1 + a0) * dt
        velocityX += 0.5f * (xAcc + accX) * dT
        velocityY += 0.5f * (yAcc + accY) * dT

        //Update previous acceleration (a0) for the next step
        accX = xAcc
        accY = yAcc

        // Check collisions
        checkBoundaries()

    }

    /**
     * Ensures the ball does not move outside the boundaries.
     * When it collides, velocity and acceleration perpendicular to the
     * boundary should be set to 0.
     */
    fun checkBoundaries() {
        // implement the checkBoundaries function
        // (Check all 4 walls: left, right, top, bottom)
        //left
        if (posX < 0) {
            posX = 0f
            velocityX = 0f
            accX = 0f
        }
        // Right Boundary
        else if (posX > backgroundWidth - ballSize) {
            posX = backgroundWidth - ballSize
            velocityX = 0f
            accX = 0f
        }

        // Top Boundary
        if (posY < 0) {
            posY = 0f
            velocityY = 0f
            accY = 0f
        }
        // Bottom Boundary
        else if (posY > backgroundHeight - ballSize) {
            posY = backgroundHeight - ballSize
            velocityY = 0f
            accY = 0f
        }
    }

    /**
     * Resets the ball to the center of the screen with zero
     * velocity and acceleration.
     */
    fun reset() {
        // implement the reset function
        // (Reset posX, posY, velocityX, velocityY, accX, accY, isFirstUpdate)

        posX = (backgroundWidth - ballSize) / 2
        posY = (backgroundHeight - ballSize) / 2
        velocityX = 0f
        velocityY = 0f
        accX = 0f
        accY = 0f
        isFirstUpdate = true
    }
}