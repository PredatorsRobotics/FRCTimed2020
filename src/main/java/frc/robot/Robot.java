/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.analog.adis16470.frc.ADIS16470_IMU;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();


   

   SpeedController m_frontLeft = new Spark(2);
   SpeedController m_rearLeft = new Spark(1);
   SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);

   SpeedController m_frontRight = new Spark(3);
   SpeedController m_rearRight = new Spark(0);
   SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);

  DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);
Joystick controller = new Joystick(0);
Servo gearshift = new Servo(9);
VictorSP sideDrive = new VictorSP(4);
SpeedController liftMotor1 = new VictorSP(5);
SpeedController liftMotor2 = new VictorSP(6);
SpeedControllerGroup liftMotors = new SpeedControllerGroup(liftMotor1, liftMotor2);
private Encoder liftMotorEncoder = new Encoder(0, 1);
double encoderValue;
double gyroValue;


ADIS16470_IMU gyro = new ADIS16470_IMU();



  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    gearshift.set(.1);
    liftMotorEncoder.reset();
    gyro.calibrate();
   
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    
   double encoderValue = liftMotorEncoder.getDistance();

   SmartDashboard.putNumber("Heading: ", gyro.getAngle());
   SmartDashboard.putNumber("Encoder: ", encoderValue);
   SmartDashboard.putNumber("ROC", gyro.getRate());
   SmartDashboard.putNumber("Instant X? ", gyro.getGyroInstantX());

   if(controller.getRawButton(10)){
     gyro.reset();     
   }

  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
   m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

    m_drive.arcadeDrive( controller.getY() *-1, controller.getX());

    if(controller.getRawButtonPressed(1)){
      gearshift.set(.7);
    }

    if(controller.getRawButtonPressed(2)){
      gearshift.set(.3);
    }

    //Side Drive System
    if(controller.getRawButton(5)){
        sideDrive.setSpeed(.35);
      } else if(controller.getRawButton(6)){
        sideDrive.set(-.35);
      } else{
        sideDrive.set(0);
      }

      // Climbing Motors set
      if(controller.getRawButton(7) && encoderValue < 1000){
        liftMotors.set(.75);
      } else if (controller.getRawButton(8) && encoderValue > 300){
        liftMotors.set(-.75);
      } else{
        liftMotors.set(0);
      }
      

      

    
    }

  

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}


//TODO: Gyro
//TODO: Autonomous
//TODO: Pnumatics for raising and lowering sidedrive