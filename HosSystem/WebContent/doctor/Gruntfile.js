module.exports = function(grunt) {
    grunt.initConfig({
		sass: {
			options: {
                includePaths: ['node_modules/bootstrap-sass/assets/stylesheets']
            },
            dist: {
				options: {
					outputStyle: 'compressed'
				},
                files: [{
					expand: true,
					cwd: 'assets/scss', /*root folder for styles*/
					src: 'main.scss', /* file path which have to be converted into css after the root folder*/
					dest: 'assets/css/',/* css file path where the converted files have to be placed*/
					ext: '.css' /*file extention for converted files*/
				}]
            }
        },
        uglify: {
          my_target: {
            files: {
               'Assets/bundles/libscripts.bundle.js': ['assets/plugins/jquery/jquery-v3.2.1.min.js','assets/plugins/bootstrap/js/bootstrap.js'], /* main js*/
              
               'Assets/bundles/vendorscripts.bundle.js': ['assets/plugins/jquery-slimscroll/jquery.slimscroll.js','assets/plugins/node-waves/waves.js'], /* coman js*/
                
               'Assets/bundles/mainscripts.bundle.js': ['assets/js/admin.js'], /* coman js*/

               'Assets/bundles/morphingsearchscripts.bundle.js': ['assets/plugins/morphingsearch/morphingsearch.js'], /* morphing search*/
               'Assets/bundles/morphingscripts.bundle.js': ['assets/js/morphing.js'], /* morphing search*/
               
               'Assets/bundles/flotscripts.bundle.js': ['assets/plugins/flot-charts/jquery.flot.js','assets/plugins/flot-charts/jquery.flot.resize.js','assets/plugins/flot-charts/jquery.flot.pie.js','assets/plugins/flot-charts/jquery.flot.categories.js','assets/plugins/flot-charts/jquery.flot.time.js'], /* Flot Chart js*/
               
               'Assets/bundles/chartscripts.bundle.js': ['assets/plugins/chartjs/Chart.bundle.js'], /* ChartJs js*/
               
               'assets/bundles/datatablescripts.bundle.js': ['assets/plugins/jquery-datatable/jquery.dataTables.min.js','assets/plugins/jquery-datatable/dataTables.bootstrap4.min.js'], /* Jquery DataTable Plugin Js  */
               
               'Assets/bundles/morrisscripts.bundle.js': ['assets/plugins/raphael/raphael.min.js','assets/plugins/morrisjs/morris.js'], /* Morris Plugin Js */
               
               'Assets/bundles/flotchartsscripts.bundle.js': ['Assets/plugins/flot-charts/jquery.flot.js','Assets/plugins/flot-charts/jquery.flot.resize.js','Assets/plugins/flot-charts/jquery.flot.pie.js','Assets/plugins/flot-charts/jquery.flot.categories.js','Assets/plugins/flot-charts/jquery.flot.time.js'], /* Morris Plugin Js */
               
               'Assets/bundles/fullcalendarscripts.bundle.js': ['assets/plugins/fullcalendar/lib/moment.min.js','assets/plugins/fullcalendar/lib/jquery-ui.custom.min.js','assets/plugins/fullcalendar/fullcalendar.min.js'],   /* calender page js */

               'Assets/bundles/jvectormapscripts.bundle.js': ['assets/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js','assets/plugins/jvectormap/jquery-jvectormap-world-mill-en.js']   /* JVectorMap Plugin Js */
                           
              }
            }
        }
                
    });
    grunt.loadNpmTasks("grunt-sass");
    grunt.loadNpmTasks('grunt-contrib-uglify');
    
    grunt.registerTask("buildcss", ["sass"]);
    grunt.registerTask("buildjs", ["uglify"]);
};
