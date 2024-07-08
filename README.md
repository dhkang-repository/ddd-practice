패키지 구조 예시
- domain: 도메인 모델을 포함합니다.
- application: 응용 서비스와 애플리케이션 로직을 포함합니다.
- infrastructure: 인프라스트럭처 관련 코드를 포함합니다.
- interfaces: 사용자 인터페이스와 API 관련 코드를 포함합니다.
```
├── domain
│   ├── common
│   │   ├── model
│   │   └── events
│   ├── customer
│   │   ├── model
│   │   ├── repository
│   │   └── service
├── application
│   ├── customer
├── infrastructure
│   ├── persistence
│   │   ├── customer
│   ├── messaging
│   └── configuration
└── interfaces
├── web
│   ├── customer
└── api
```
