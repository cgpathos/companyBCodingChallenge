# Company B 면접 과제

> 과제로 제출했던 앱에서 회사정보를 제거한 버젼  
> 커밋도 작업 진행이 어떤 순서로 했는지 알 수 있게 했는데 올린 수 없어서 아쉽다. 

## 1. 과제 내용

1. Github 사용자를 검색하고, 즐겨찾기를 관리하는 앱을 개발하려 합니다.
    * Github 사용자 검색 API(https://developer.github.com/v3/search/#search-users)를 이용하여 사용자를 검색 및 표시
    * 즐겨찾기한 사용자를 로컬 DB에 저장
    * 즐겨찾기한 사용자를 로컬 DB에서 검색
  
1.  UI는 두개의 탭으로 구성됩니다.
    * 탭1 : API
      - 검색어 입력창에 검색어를 입력하고, 검색 버튼을 클릭하면 Github 사용자 검색 API를 호출하여 사용자를 검색합니다. 검색 API에 사용되는 필드는 사용자 이름으로 제한합니다.
      - 검색 결과는 최대 100까지만 보여줍니다. 이를 위해 검색 API 호출 시 page는 1, per_page는 100으로 설정합니다. 자세한 내용은 Github 검색 API 문서를 참고해주세요.
      - Github 사용자 검색 API 응답을 받아 사용자 리스트를 보여줍니다. 아이템 뷰는 프로필 이미지, 사용자 이름, 즐겨찾기 여부를 보여줍니다. 사용자가 이미 즐겨찾기에 추가되어 있으면 이를 표시합니다.
      - 사용자 이름 순으로 정렬합니다. 사용자 이름의 초성이나 알파벳을 기준으로 헤더를 붙여줍니다.
      - 아이템 뷰를 누르면 즐겨찾기로 추가합니다. 이미 즐겨찾기된 사용자의 아이템 뷰를 누르면 즐겨찾기를 취소합니다.
      - 사용자 검색 화면에서 해당 사용자 아이템 뷰의 즐겨찾기 여부를 갱신하고, 즐겨찾기 화면에서도 해당 사용자를 추가 또는 삭제합니다. 
    * 탭2 : 로컬
      - 검색어 입력창에 검색어를 입력하고 검색 버튼을 클릭하면, 로컬 DB에서 이름에 검색어가 포함된 사용자를 검색합니다. 검색 필드는 사용자 이름으로 제한합니다.
      - 검색어와 매칭된 모든 사용자 리스트를 보여줍니다. 아이템 뷰는 API 검색화면의 아이템 뷰와 같습니다.
      - 사용자 이름 순으로 정렬합니다. 사용자 이름의 초성이나 알파벳을 기준으로 헤더를 붙여줍니다.
      - 아이템 뷰를 누르면 즐겨찾기를 취소합니다. 사용자 검색 화면에서 해당 사용자 아이템 뷰의 즐겨찾기 여부를 갱신하고, 즐겨찾기 화면에서도 해당 사용자를 삭제합니다.

---

## 2. 구현 내용

* clean architecture MVVM 형태로 앱 구현
* 각 레이어들간 의존성 처리는 dagger Hilt를 이용해 처리
* 요구사항인 API17에서 github API에서 지원하는 프로토콜이 없는 문제로 연동이 불가능해 하위버젼은 테스트하지 않기로 하고, 최신버젼(API30)에서만 동작을 검증함
* 서버 이슈로 동작은 안되지만 네트워크라이브러리(okhttp)는 API17까지 지원되는 버전으로 설정된 상태
* 즐겨찾기 추가/삭제는 long click으로 구현
* api 검색 쿼리는 `{name} in:fullname` 으로 구현  
  (`{name} in:login`은 검색결과가 다양하지않아서 ㄱ,ㄴ,ㄷ형식 헤더를 제대로 볼 수 없어 수정)


## Domain Layout

> today.pathos.companyb.codingtest.domain 페키지 하위에 존재

1. useCase,entity, repositiry로 구성
1. 비즈니스 로직 구현은 useCase 에서 담당하면 useCase안에 useCase를 체이닝하는 경우도 존재
1. entity는 비즈니스 로직에 사용되는 데이터를 정의한 data class  
  (이 과제에는 편의상 앱 전반에 이 entitiy 클래스를 데이터 클래스로 사용)
1. repository는 인터페이스 형태로 존재

## Data Layout

> today.pathos.companyb.codingtest.data 페키지 하위에 존재

1. Domain Layer의 repository 구현체가 여기에 위치
1. repository 구현체에 특화된 모델 클래스가 존재(Dto, tableEntity)하며 
  Domain Layer entity 로 상호 변환을 위한 mapper 가 위치함

## Presentation Layer

> today.pathos.companyb.codingtest.presentation 페키지 하위에 존재

### ViewModel Layer

1. viewModel, viewState 가 여기에 위치
1. viewModel 은 AAC의 ViewModel을 사용
1. viewModel 은 MainViewModel 하나면 존재하면 ViewPager 안에 fragment들에서는 부모의 MainViewModel을 함꼐 사용

### View Layer

1. activity,fragment가 여기에 위치
1. viewpager adapter, recyclerview adapter, recyclerview item viewHolder도 여기에 위치
1. 데이터 바인딩 관련 클래스도 여기에 위치

## test code

1. useCase 들의 unit test 작성
1. MainViewModel unit test 작성
   - 검색 호출시 뷰상태변화를 테스트
