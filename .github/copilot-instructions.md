# Copilot Agent POC Repository

**ALWAYS follow these instructions first and fallback to additional search and context gathering ONLY if the information in these instructions is incomplete or found to be in error.**

This is a minimal proof-of-concept repository demonstrating GitHub Copilot agent capabilities. The repository currently contains minimal content and serves as a foundation for testing and experimentation.

## Current Repository State

The repository is in a minimal state with:
- Single README.MD file containing "TEST" content
- No build infrastructure, dependencies, or source code
- No package management files (package.json, requirements.txt, etc.)
- No test infrastructure
- No CI/CD pipelines beyond dynamic Copilot workflows

**DO NOT attempt to build, test, or run anything yet** - there is no code to build or run.

## Working Effectively

### Repository Setup
- Clone repository: `git clone https://github.com/LucasKonrath/copilot-agent-poc.git`
- Navigate to repository: `cd copilot-agent-poc`
- Check current state: `ls -la` (should show only README.MD and .git/)

### When Adding New Code
Follow these practices when this repository evolves:

#### For Node.js/JavaScript Projects
- Install dependencies: `npm install` -- NEVER CANCEL. Set timeout to 10+ minutes for large projects
- Build project: `npm run build` -- NEVER CANCEL. Build may take 15-45 minutes. Set timeout to 60+ minutes
- Run tests: `npm test` -- NEVER CANCEL. Tests may take 10-30 minutes. Set timeout to 45+ minutes
- Start development server: `npm run dev` or `npm start`

#### For Python Projects  
- Create virtual environment: `python -m venv venv`
- Activate environment: `source venv/bin/activate` (Linux/Mac) or `venv\Scripts\activate` (Windows)
- Install dependencies: `pip install -r requirements.txt` -- NEVER CANCEL. Set timeout to 15+ minutes
- Run tests: `python -m pytest` or `python -m unittest` -- NEVER CANCEL. Set timeout to 30+ minutes

#### For Java Projects
- Build with Maven: `mvn clean install` -- NEVER CANCEL. May take 20-60 minutes. Set timeout to 90+ minutes
- Build with Gradle: `./gradlew build` -- NEVER CANCEL. May take 15-45 minutes. Set timeout to 60+ minutes
- Run tests: `mvn test` or `./gradlew test` -- NEVER CANCEL. Set timeout to 45+ minutes

#### For Go Projects
- Download dependencies: `go mod download` -- NEVER CANCEL. Set timeout to 10+ minutes
- Build: `go build` -- Usually fast, but set timeout to 10+ minutes for large projects
- Run tests: `go test ./...` -- NEVER CANCEL. Set timeout to 30+ minutes

## Critical Build and Test Guidelines

### TIMING EXPECTATIONS
- **NEVER CANCEL long-running commands** - builds and tests in real projects commonly take 15-60+ minutes
- **ALWAYS set explicit timeouts** of at least 60 minutes for build commands
- **ALWAYS set explicit timeouts** of at least 45 minutes for test commands
- If a command appears to hang, wait at least 60 minutes before considering alternatives

### Common Build Times by Project Type
- Small Node.js projects: 2-10 minutes
- Large Node.js projects: 15-45 minutes  
- Java/Maven projects: 20-60 minutes
- Python projects with dependencies: 5-20 minutes
- Go projects: 1-15 minutes
- C/C++ projects: 10-90+ minutes

## Validation Requirements

### Manual Testing Scenarios
When code is added to this repository, ALWAYS validate changes with these scenarios:

#### Web Applications
- Start the application and navigate to the home page
- Test core user workflows (login, main functionality, logout)
- Verify all major UI components render correctly
- Test responsive design on different screen sizes
- Take screenshots of key pages to document functionality

#### CLI Applications  
- Run `--help` command to verify usage instructions
- Execute primary commands with sample data
- Verify output files are created correctly
- Test error handling with invalid inputs

#### APIs/Services
- Start the service and verify it responds on expected port
- Test primary endpoints with curl or HTTP client
- Validate response formats and status codes
- Test authentication if applicable

#### Libraries/Packages
- Import/require the library in a test script
- Exercise main API functions
- Verify expected return values and behaviors

## Code Quality and CI Preparation

When adding code to this repository:

### Linting and Formatting
- Run linter before committing: language-specific (e.g., `npm run lint`, `flake8`, `golint`)
- Run formatter: `npm run format`, `black .`, `gofmt`, etc.
- Fix all linting errors before committing

### Pre-commit Checklist
- All builds complete successfully (wait for full completion)
- All tests pass (wait for full test suite)
- Linting passes without errors
- Manual validation scenarios completed
- Documentation updated if needed

## Repository Navigation

### Key Locations (when code is added)
- Source code: typically `src/`, `lib/`, or root directory
- Tests: typically `test/`, `tests/`, `__tests__/`, or `*_test.go`
- Documentation: `docs/`, `README.md`, inline code comments
- Configuration: `package.json`, `requirements.txt`, `go.mod`, `pom.xml`, etc.
- Build scripts: `Makefile`, `build.sh`, npm scripts, etc.

### Common File Patterns
- Entry points: `main.py`, `index.js`, `main.go`, `App.java`
- Configuration: `.env`, `config.json`, `settings.py`
- Dependencies: `package-lock.json`, `yarn.lock`, `go.sum`, `requirements.txt`

## Troubleshooting

### Build Failures
- Check dependency installation completed successfully
- Verify correct language/runtime version installed
- Review build logs for specific error messages
- Check environment variables and configuration

### Test Failures
- Run tests individually to isolate failures
- Check test data and fixtures are properly set up
- Verify test environment configuration
- Review test logs for specific assertion failures

### Development Server Issues
- Verify all dependencies installed
- Check port availability (common ports: 3000, 8000, 8080)
- Review server logs for startup errors
- Confirm environment variables are set correctly

## Important Notes

- This repository is currently minimal and experimental
- Always validate that commands work before documenting them as successful
- When adding substantial code, update these instructions with specific project details
- Include actual command outputs and timing measurements in instruction updates
- Document any platform-specific requirements or limitations discovered

## Future Development

When this repository is expanded with actual code:
- Update the "Current Repository State" section
- Add specific build commands and validated timings
- Include actual validation scenarios based on the application type
- Document any discovered workarounds or platform limitations
- Add specific dependency installation instructions with exact versions