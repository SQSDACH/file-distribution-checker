File Distribution Checker
=====

The FileDistributionChecker can be used to search for copies of a file with the same name. It search for the file name in the given directory and its sub directories. For each found file with the specified file name a checksum will be calculated. Finally the findings are grouped by checksum and the groups are ordered by its size (descending).


Usage
-------
### Build it

    $ gradle jar
    :compileJava
    :processResources UP-TO-DATE
    :classes
    :jar

    BUILD SUCCESSFUL

    Total time: 2.984 secs

### Analyze single file

    $ ./FileDistributionChecker -f ./LICENSE

    File variants of 'LICENSE'
    ---------------------------------------------------
    1) #1 times (md5: 4226DF0947B22024E5FED1B632E6756D)
      /projects/file-distribution-checker/LICENSE

### Analyze distribution of LICENSE files

    $ ./FileDistributionChecker -d ~/.kanso -n LICENSE

    File variants of 'LICENSE'
    ---------------------------------------------------
    1) #6 times (md5: 64A378B2B01424FE22D54BC626175994)
      /Users/thomo/.kanso/cache/attachments/0.0.10/package/node_modules/async/LICENSE
      /Users/thomo/.kanso/cache/duality/0.0.18/package/node_modules/kanso-utils/node_modules/async/LICENSE
      /Users/thomo/.kanso/cache/dust/0.3.0/package/node_modules/async/LICENSE
      /Users/thomo/.kanso/cache/modules/0.0.13/package/node_modules/async/LICENSE
      /Users/thomo/.kanso/cache/properties/0.0.12/package/node_modules/kanso-utils/node_modules/async/LICENSE
      /Users/thomo/.kanso/cache/settings/0.0.12/package/node_modules/kanso-utils/node_modules/async/LICENSE
    2) #6 times (md5: 8EAB89930E0B484CD991ED4A155F74F7)
      /Users/thomo/.kanso/cache/attachments/0.0.10/package/node_modules/kanso-utils/node_modules/mime/LICENSE
      /Users/thomo/.kanso/cache/duality/0.0.18/package/node_modules/kanso-utils/node_modules/mime/LICENSE
      /Users/thomo/.kanso/cache/dust/0.3.0/package/node_modules/kanso-utils/node_modules/mime/LICENSE
      /Users/thomo/.kanso/cache/modules/0.0.13/package/node_modules/mime/LICENSE
      /Users/thomo/.kanso/cache/properties/0.0.12/package/node_modules/kanso-utils/node_modules/mime/LICENSE
      /Users/thomo/.kanso/cache/settings/0.0.12/package/node_modules/kanso-utils/node_modules/mime/LICENSE
    3) #3 times (md5: 9EAF14330481B774E6E143D7E1F2D02E)
      /Users/thomo/.kanso/cache/duality/0.0.18/package/node_modules/underscore/LICENSE
      /Users/thomo/.kanso/cache/dust/0.3.0/package/node_modules/underscore/LICENSE
      /Users/thomo/.kanso/cache/properties/0.0.12/package/node_modules/underscore/LICENSE
    4) #1 times (md5: 92DB1E30FA190EAB0BA4E46FD0E058BC)
      /Users/thomo/.kanso/cache/dust/0.3.0/package/node_modules/dust/LICENSE

### Format the output

When running with the -t &lt;template&gt; parameter the output is rendered using the given template (using [Freemarker](http://freemarker.org)). The output is written to stdout.

    $ ./FileDistributionChecker -d ~/.kanso -n LICENSE -t templates/simple.ftl > report.html

Resulting report

![Example HTML report](doc/htmlreport.png?raw=true "Example HTML report")

#### Define your own template

Use the small [template sample](src/main/dist/templates/simple.ftl) as a master for creating your own report templates.

The underlying data structure is

```json
{
  "name": "???",
  "groups": [
    {
      "hash": "1",
      "files": [
        "a/data.txt",
        "a/b/data.txt"
      ]
    },
    {
      "hash": "2",
      "files": [
        "a/c/data.txt"
      ]
    }
  ]
}
```

License
-------

**MIT** - see LICENSE file for details.

Author Information
------------------

Thomas Mohaupt https://github.com/sqs-dach/file-distribution-checker
