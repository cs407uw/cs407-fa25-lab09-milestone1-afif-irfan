package com.cs407.lab09

import android.hardware.Sensor
import android.hardware.SensorEvent
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BallViewModel : ViewModel() {

    private var ball: Ball? = null
    private var lastTimestamp: Long = 0L

    // Expose the ball's position as a StateFlow
    private val _ballPosition = MutableStateFlow(Offset.Zero)
    val ballPosition: StateFlow<Offset> = _ballPosition.asStateFlow()

    /**
     * Called by the UI when the game field's size is known.
     */
    fun initBall(fieldWidth: Float, fieldHeight: Float, ballSizePx: Float) {
        if (ball == null) {
            ball = Ball(fieldWidth, fieldHeight, ballSizePx)
            ball?.let { b ->
                _ballPosition.value = Offset(b.posX, b.posY)
            }
        }
    }

    /**
     * Called by the SensorEventListener in the UI.
     */
    fun onSensorDataChanged(event: SensorEvent) {
        // Ensure ball is initialized
        val currentBall = ball ?: return
        val SCALE_FACTOR = 400f

        if (event.sensor.type == Sensor.TYPE_GRAVITY) {

            if (lastTimestamp == 0L) {
                lastTimestamp = event.timestamp
                return
            }

            val dT = (event.timestamp - lastTimestamp) / 1_000_000_000f


            lastTimestamp = event.timestamp

            currentBall.updatePositionAndVelocity(
                xAcc = -event.values[0] * SCALE_FACTOR,
                yAcc = event.values[1] * SCALE_FACTOR,
                dT = dT
            )

            _ballPosition.update { Offset(currentBall.posX, currentBall.posY) }
        }
    }

    fun reset() {
        // Reset the ball's state
        ball?.reset()

        // Update the StateFlow with the reset position
        ball?.let { b ->
            _ballPosition.value = Offset(b.posX, b.posY)
        }

        // Reset the lastTimestamp
        lastTimestamp = 0L
    }
}