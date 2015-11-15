File Distribution Checker
=====

The FileDistributionChecker can be used to search for copies of a file with the same name. It search for the file name in the given directory and its sub directories. For each found file with the specified file name a checksum will be calculated. Finally the findings are grouped by checksum and the groups are ordered by its size (descending). 
 

Usage
-------

	Usage:
	  FileDistributionChecker -h
	  FileDistributionChecker -d <dir> -n <file name>
	  FileDistributionChecker -f <file>
	Analyse the distribution of files with a given name in a directory (recursive)
	or a single file.
	  -d,--dir <dir>          start dir for search
	  -f,--file <file>        check this file
	  -h,--help               show usage message
	  -n,--name <file name>   file name to search for
	Please report issues at https://github.com/sqs-dach/file-distribution-checker

Note: FileDistributionChecker is an alias for java -jar <path to FileDistributionChecker-X.X.jar>

License
-------

**MIT** - see LICENSE file for details.

Author Information
------------------

Thomas Mohaupt https://github.com/sqs-dach/file-distribution-checker