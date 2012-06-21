# Immutant cluster demo (w/o multicast)

1. Download the latest build from http://immutant.org/builds/, take it
   as a customised JBoss version.
2. Install the provided standalone-ha.xml. For two nodes you just need
   to change the IP addresses (search for 10.0.0.). More nodes should
   be easy to add. See
   http://torquebox.org/2x/builds/html-docs/production-setup.html#clustering-without-multicast
   for details of the unicast configuration. If you have multicast
   available you should be fine with the stock configuration.
3. Run standalone.sh on all nodes.
4. Build the application with "lein immutant archive"; this requires
   https://github.com/immutant/lein-immutant. Alternatively, use the
   provided jbctest2.ima.
5. Deploy to all nodes using the JBoss CLI, for example.
6. Browse to http://node1.example/jbctest2/, enter some data, then check
   the other node(s) to see if HornetQ messaging and Infinispan caching do
   work.

Good luck!  :)
