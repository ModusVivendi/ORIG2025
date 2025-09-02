package org.firstinspires.ftc.teamcode.Functions.DataLogger;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.lang.StringBuilder;
import java.util.Calendar;

import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Functions.AccelerationDetector;
import org.firstinspires.ftc.teamcode.Functions.Move;
import org.firstinspires.ftc.teamcode.Functions.RotationDetector;

public class DataLogger {

    private Writer writer;
    private StringBuffer lineBuffer;
    private StringBuilder lineRecord;
    private long msInitTime;
    private long nsInitTime;

    private static final String[] headerFields = new String[] {"runtime",
            "className", "leftMotor", "rightMotor", "leftMotorBack",
            "rightMotorBack", "currentDirection",  "voltage",
            "currentAnglePositive", "currentAngleRaw", "accelX", "accelY", "accelZ"};
    private static final String CSV_HEADER2 = "runtime,className,leftMotor,rightMotor,leftMotorBack,rightMotorBack,currentDirection,voltage,currentAnglePositive,currentAngleRaw,accelX,accelY,accelZ";

    // Movement variables
    RotationDetector rotationDetector;
    AccelerationDetector accelerationDetector;
    Move move;

    // Sensor variables
    VoltageSensor voltageSensor;

    // File variables
    String dataLogFileName;
    String[] headers;
    String className;

    public void checkDirectory(File directory) {
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    // FIX: Added missing 'c' to make this method 'public'
    public void checkFile(File file) {
        try {
            File parentDirectory = file.getParentFile();
            checkDirectory(parentDirectory);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }

    public String createDateDirectoryName() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Calendar months are zero-indexed
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "_" + month + "_" + day;
    }

    // FIXED CONSTRUCTOR: Complete and functional
    public DataLogger(RotationDetector _rotationDetector,
                      VoltageSensor _voltageSensor,
                      Move _move,
                      AccelerationDetector _accelerationDetector,
                      String _className) {
        Date today = new Date();
        String YMD = createDateDirectoryName();
        dataLogFileName = new String(today.toString() + "_data" + ".csv");

        File directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File subDirectoryName = new File(directoryPath.getAbsolutePath() + "/" + YMD);
        File filePath = new File(directoryPath.getAbsolutePath() + "/" + YMD + "/" + dataLogFileName);

        String fileErrorMsg = "Missing directory";
        try {
            checkDirectory(directoryPath);
            checkDirectory(subDirectoryName);
            fileErrorMsg = directoryPath.getCanonicalPath();
            checkFile(filePath);
        }
        catch (IOException e) {
            System.out.println("-> First error: " + filePath.getAbsoluteFile() +
                    "\n" + fileErrorMsg + "\n" + e.toString());
        }

        fileErrorMsg = "File not found";
        try {
            writer = new FileWriter(filePath);
            lineBuffer = new StringBuffer(128);
            // Can add header writing here if needed
        }
        catch (IOException e) {
            System.out.println("-> Second error: " + filePath.getAbsoluteFile()
                    + "\n" + fileErrorMsg + "\n" + e.toString());
        }

        // Initialize member variables
        rotationDetector = _rotationDetector;
        voltageSensor = _voltageSensor;
        accelerationDetector = _accelerationDetector;
        move = _move;

        headers = new String[] {"runtime", "className", "leftMotor", "rightMotor",
                "leftMotorBack", "rightMotorBack", "currentDirection",
                "voltage", "currentAngleRaw",
                "accelX", "accelY", "accelZ"};
        className = _className;
    }

    // Add essential methods for data logging functionality
    public void writeDataLine(double runtime) {
        if (writer == null) return;

        try {
            // Example data line - customize based on your needs
            String dataLine = String.format("%.3f,%s,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f\n",
                    runtime, className,
                    move != null ? move.ReadMotor(1) : 0.0,
                    move != null ? move.ReadMotor(2) : 0.0,
                    move != null ? move.ReadMotor(3) : 0.0,
                    move != null ? move.ReadMotor(4) : 0.0,
                    move != null ? move.ReturnCurrentDirection() : 0.0,
                    voltageSensor != null ? voltageSensor.getVoltage() : 0.0,
                    rotationDetector != null ? rotationDetector.ReturnRotation() : 0.0,
                    accelerationDetector != null ? accelerationDetector.getAccelX() : 0.0,
                    accelerationDetector != null ? accelerationDetector.getAccelY() : 0.0,
                    accelerationDetector != null ? accelerationDetector.getAccelZ() : 0.0
            );

            writer.write(dataLine);
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error writing data: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing file: " + e.getMessage());
        }
    }
}