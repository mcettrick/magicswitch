# magicswitch

Control a USB relay using a custom scratch block.

Like any other experimental scratch HTTP extensions, it only works with the scratch offline editor (Scratch 2.0). It can't work with the version at https://scratch.mit.edu.

I used it with this USB relay:

http://www.mpja.com/Relay-SPDT-10A-250VAC-USB-Controlled-LCUS-1/productinfo/31948+MP

If the relay is not attached to a serial port at this location on the filesystem:

/dev/ttyUSB0

Then it won't work. I only used it with Ubuntu, so this port is hard-coded in source code in Relay.java.


directories:

scratch_block - Scratch block definition.
server - Source code for Spring boot microservice that the scratch block communicates with.

Requirements:

Java 1.8

## Quick Start

Install scratch 2.0 offine editor here: https://scratch.mit.edu/scratch2download/
Requires adobe air.

Plug in the USB relay
   
start the relay service. It runs on port 8080. Run this command from the magicswitch directory:
java -jar magicswitch.jar

optionally test by browsing to this address. Each time this page is loaded it will toggle the switch on / off
http://localhost:8080/toggle

In scratch, shift-click on File, then Import Experimental HTTP Extension. Browse to magicswitch/scratch_block/magicswitch.json.

The block will be available under More Blocks, named "MagicSwitch".

## Building from source

Install maven

In the magicswitch/server directory:
mvn clean package

If the switch isn't plugged in:
mvn clean package -DskipTests

This will skip the tests, which need the switch to show up as

/dev/ttyUSB0
