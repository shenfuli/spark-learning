import sys, getopt
from faker import Factory
#----------------------------------------------------------------------
def main(argv):
    """"""
    delm = '|'
    num_rows = 4000000
    try:
        opts, args = getopt.getopt(argv,"r:",["rows="])
    except getopt.GetoptError:
        print 'Usage: datacreation1.py -r "number_rows"'
        sys.exit(2)
    for opt, arg in opts:
        if opt in("-r", "--rows" ):
            num_rows = arg
        else:
            assert False, "unhandled option"
    fake = Factory.create()
    create_data(fake,delm,num_rows)

def create_data(fake,delm,num_rows):
    for i in range(int(num_rows)):
        record = ""

        print record

if __name__ == "__main__":
    main(sys.argv[1:])