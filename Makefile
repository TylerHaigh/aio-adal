CC=mvn
ENV=dev
RUNTIME_KEY=Invalid
RUNTIME_ENV=test
VM_ARGS= -Druntime.key=$(RUNTIME_KEY) -Denvironment=$(RUNTIME_ENV)
SKIP_TESTS=false
MVN_SKIP_MUNIT=-DskipMunitTests
SUITE=*
TEST=*

build:
ifeq ($(SKIP_TESTS),true)
	$(CC) package $(PROFILES) $(MVN_SKIP_MUNIT)
else
	$(CC) package $(PROFILES) $(VM_ARGS)
endif

clean:
	$(CC) clean

clean-build:
ifeq ($(SKIP_TESTS), true)
	$(CC) clean package $(PROFILES) $(MVN_SKIP_MUNIT)
else
	$(CC) clean package $(PROFILES) $(VM_ARGS)
endif

test:
	$(CC) test $(PROFILES) $(VM_ARGS)

test-suite:
ifeq ($(TEST), *)
	$(CC) test $(PROFILES) $(VM_ARGS) -Dmunit.test=$(SUITE)
else
	$(CC) test $(PROFILES) $(VM_ARGS) -Dmunit.test=$(SUITE)#$(TEST)
endif

#deploy:
#	$(CC) deploy $(PROFILES) $(VM_ARGS)
