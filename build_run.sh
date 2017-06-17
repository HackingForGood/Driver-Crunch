
#!/bin/bash


#this runs on apollo

#Steps:

#Delete everything under target
#Do a mvn clean build and make the war file, war file is in jenkins.

#delete the .war file from /var/lib/tomcat7/webapps/winnow_crunch.war  immediate
#delete the directory from /var/lib/tomcat7/webapps/winnow_crunch      immediate
#place the war file to /var/lib/tomcat7/webapps/winnow_crunch.war      immediate
#force restart tomcat                                                  7 seconds


echo "rm -rf target"
sudo rm -rf target
echo "mvn package"
mvn clean package
echo "rm war"
sudo rm /var/lib/tomcat7/webapps/SpringMVC.war
echo "rm directory"
sudo rm -rf /var/lib/tomcat7/webapps/SpringMVC
echo "copy in new war"
sudo cp target/SpringMVC.war /var/lib/tomcat7/webapps/SpringMVC.war
echo "reboot"
sudo /etc/init.d/tomcat7 restart

exit 0  #mark success

#cp target/SpringMVC.war /mnt/apollo/SpringMVC.war
#ssh 10.0.0.3 -p 10522 'sudo rm /var/lib/tomcat7/webapps/SpringMVC.war'
#sleep 5
#ssh 10.0.0.3 -p 10522 'cp /home/el/SpringMVC.war /var/lib/tomcat7/webapps/SpringMVC.war'
#sleep 4
#ssh 10.0.0.3 -p 10522 'sudo /etc/init.d/tomcat7 restart'
#sleep 4
#
