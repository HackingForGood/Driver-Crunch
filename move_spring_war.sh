#!/bin/bash

#READ THIS:
#You have to mount the /mnt/apollo first like this: 
#sshfs el@10.0.0.3:/home/el/ -p 10522 /mnt/apollo


#Pseudocode
#1.  Move target/SpringMVC.war to apollo: /var/lib/tomcat7/webapps/SpringMVC.war
#2.  On apllo run: etc/init.d/tomcat7 restart


cp target/SpringMVC.war /mnt/apollo/SpringMVC.war
ssh 10.0.0.3 -p 10522 'sudo rm /var/lib/tomcat7/webapps/SpringMVC.war'
sleep 5
ssh 10.0.0.3 -p 10522 'cp /home/el/SpringMVC.war /var/lib/tomcat7/webapps/SpringMVC.war'
sleep 4
ssh 10.0.0.3 -p 10522 'sudo /etc/init.d/tomcat7 restart'
sleep 4


#rm stuff.htmle
#cp ../pr2/df3.rds .

#Kill evince if it's hanging out in the background
#ps aux | grep -ie "evince stuff.pdf" | awk '{print $2}' | xargs kill -9
#
#echo "1 remove stuff.html "
#rm stuff.html
#
#echo "2 stuff.pdf"
#rm stuff.pdf
#
#
#echo "3 run Rscript convert.r to make the stuff.html"
#Rscript convert.r
#
#
#echo "4 repair the stuff.html"
#sed -ie 's/F8F8F8/E2E2E2 !important/g' stuff.html
#
#sed -ie 's/monospace;/monospace; background-color: #fcf9ee !important/g' stuff.html
#
#echo "5 remote delete the stuff.pdf"
#rm /mnt/apollo/stuff.pdf
#rm /mnt/apollo/stuff.html
#
#rm -rf /mnt/apollo/figure
#
#echo "6 move stuff.html to apollo"
#cp stuff.html /mnt/apollo/stuff.html
#cp -r ./figure /mnt/apollo
#
#echo "7 remote run wkhtmltopdf "
##Use this for landscape
##ssh 10.0.0.3 -p 10522 '/usr/bin/xvfb-run --server-args="-screen 0, 1024x768x24" wkhtmltopdf -O landscape /home/el/stuff.html /home/el/stuff.pdf'
#
##portrait pdf
#ssh 10.0.0.3 -p 10522 '/usr/bin/xvfb-run --server-args="-screen 0, 1024x768x24" wkhtmltopdf /home/el/stuff.html /home/el/stuff.pdf'
#
#echo "8 when complete, pull the stuff.pdf file back to here"
#
#cp /mnt/apollo/stuff.pdf .
#
#
#
#







