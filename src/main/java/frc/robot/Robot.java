/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
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
  
  private SpeedController frontLeftMotor;
  private SpeedController frontRightMotor;
  private SpeedController rearLeftMotor;
  private SpeedController rearRightMotor;
  private SpeedController sideDrive;

  private SpeedControllerGroup leftSideGroup;
  private SpeedControllerGroup rightSideGroup;
  private Joystick driverJoystick;
  private DifferentialDrive m_drive;
  private Servo servo;




  



  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    frontLeftMotor = new Spark(0);
    rearLeftMotor = new Spark(1);
    frontRightMotor = new Spark(2);
    rearRightMotor = new Spark(3);
    sideDrive = new Spark(4);

    leftSideGroup = new SpeedControllerGroup(frontLeftMotor, rearLeftMotor);
    rightSideGroup = new SpeedControllerGroup(frontRightMotor, rearRightMotor);
    driverJoystick = new Joystick(0);
    
    servo = new Servo(9);

    //m_drive = new DifferentialDrive(leftSideGroup, rightSideGroup);
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
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
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

    m_drive = new DifferentialDrive(leftSideGroup, rightSideGroup);
    m_drive.arcadeDrive(driverJoystick.getX(), driverJoystick.getZ());

    boolean hiSpeed = driverJoystick.getRawButtonPressed(1);
    if(hiSpeed){
      servo.set(0.5);
    }
    else{
      servo.set(0);
    }

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}


//TODO: Servo System
//Be able to set between 2 locations based on button input or speed sensing 

//TODO: Gyro

//TODO: Autonomous

//TODO: Sidedrive

//TODO: Pnumatics for raising and lowering sidedrive

